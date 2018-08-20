package levels;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cameras.CameraFollow;
import cameras.CameraScroll;
import cameras.CameraStatic;
import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.Entity;
import gameobjects.entities.items.Coin;
import gameobjects.entities.items.OneUp;
import gameobjects.entities.items.SuperMushroom;
import gameobjects.entities.mobs.Goomba;
import gameobjects.entities.mobs.Koopa;
import gameobjects.entities.mobs.Player;
import gameobjects.tiles.Block;
import gameobjects.tiles.Brick;
import gameobjects.tiles.EmptyBlock;
import gameobjects.tiles.Ground;
import gameobjects.tiles.Pipe;
import gameobjects.tiles.Spike;
import gameobjects.tiles.itemtiles.ItemBrick;
import gameobjects.tiles.itemtiles.ItemTile;
import gameobjects.tiles.itemtiles.KaizoBlock;
import gameobjects.tiles.itemtiles.QBlock;
import gameobjects.tiles.semisolids.BowserBridge;
import gameobjects.tiles.semisolids.Bridge;
import gameobjects.tiles.semisolids.Cloud;
import gameobjects.tiles.semisolids.MovingPlatform;
import gameobjects.tiles.semisolids.OneUsePlatform;
import gameobjects.tiles.semisolids.TreePlatform;
import maths.Vec2;
import maths.Vec4;

public class LevelCreator {

	public static final Map<String, Class<? extends GameObject>> CLASS_MAP = new HashMap<>();
	static {
		CLASS_MAP.put("player", Player.class);
		CLASS_MAP.put("goomba", Goomba.class);
		CLASS_MAP.put("koopa", Koopa.class);
		
		CLASS_MAP.put("block", Block.class);
		CLASS_MAP.put("brick", Brick.class);
		CLASS_MAP.put("empty-block", EmptyBlock.class);
		CLASS_MAP.put("ground", Ground.class);
		CLASS_MAP.put("pipe", Pipe.class);
		CLASS_MAP.put("spike", Spike.class);
		CLASS_MAP.put("item-brick", ItemBrick.class);
		CLASS_MAP.put("qblock", QBlock.class);
		CLASS_MAP.put("kaizo", KaizoBlock.class);
		CLASS_MAP.put("bowser-bridge", BowserBridge.class);
		CLASS_MAP.put("bridge", Bridge.class);
		CLASS_MAP.put("cloud", Cloud.class);
		CLASS_MAP.put("moving-platform", MovingPlatform.class);
		CLASS_MAP.put("oneuse-platform", OneUsePlatform.class);
		CLASS_MAP.put("falling-platform", OneUsePlatform.class);
		CLASS_MAP.put("tree-platform", TreePlatform.class);
		
		CLASS_MAP.put("super-mushroom", SuperMushroom.class);
		CLASS_MAP.put("one-up", OneUp.class);
		CLASS_MAP.put("coin", Coin.class);
	}
	
	private List<String> log;
	private List<GameObject> objects;
	
	private String filepath;
	private Level level;
	
	private long timeElapsed;
	
	public LevelCreator(String filepath, Level level) {
		long start = System.nanoTime();
		this.filepath = filepath;
		this.level = level;
		log = new ArrayList<>();
		objects = new LinkedList<>();
		
		createLevel();
		Collections.sort(objects, sortByZ);
		
		if(!level.isCameraSet()) level.setCamera(new CameraStatic(new Vec2()));
		
		//store time took to complete level creation
		timeElapsed = System.nanoTime() - start;
	}
	
	private void createLevel(){
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(Paths.get(filepath));
			
			ArrayList<String> objsrc = splitByOuterBrackets(bytes);
			if(objsrc != null) createObjectsFromSrc(objsrc);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//split the data by the outer-most brackets.
	private ArrayList<String> splitByOuterBrackets(byte[] bytes){
		String rawstr = new String(bytes);
		ArrayList<String> objsrc = new ArrayList<>();
		
		int opencnt = 0; //number of open brackets
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == '{') {
				opencnt++;
				for(int j = i+1; j < bytes.length; j++) {
					if(bytes[j] == '{') opencnt++;
					else if(bytes[j] == '}') {
						opencnt--;
						if(opencnt == 0) {
							objsrc.add(rawstr.substring(i, j+1));
							i = j+1;
							break;
						}
					}
				}
			}
		}
		
		if(opencnt > 0) {
			log.add("Error: bracket mismatch! too many open brackets.");
			return null;
		}
		
		return objsrc;
	}

	private void createObjectsFromSrc(ArrayList<String> objsrc) {
		boolean camsFound = false; //remember if a camera has already been declared in file
		for(int i = 0; i < objsrc.size(); i++) {
			//get the type of GameObject
			String src = objsrc.get(i).toLowerCase();
			int pos = src.indexOf("type:");
			if(pos == -1) {
				log.add("Warning: type not defined in block " + i);
				continue;
			}
			
			//see if the type exists in the classmap
			String type = parseStringArgs(src, "type:")[0];
			if(CLASS_MAP.containsKey(type)) {
				try {
					@SuppressWarnings("unchecked")
					List<GameObject> objs = (List<GameObject>) CLASS_MAP.get(type).getMethod("createFromString", String.class).invoke(null, src);
					
					//loop through created GameObjects for important Objects
					for(GameObject o : objs) {
						//System.out.println(o.getClass().getSimpleName() + " Created.");
						if(o instanceof Entity) {
							((Entity) o).setLevel(level);
						}else if(o instanceof ItemTile) {
							((ItemTile) o).setLevel(level);
						}
					}
					objects.addAll(objs);
				} catch (NoSuchMethodException e) {
					log.add("Warning: " + CLASS_MAP.get(type).getSimpleName() + " cannot be created from a string.");
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else if(type.equals("level")){
				int intArgs[] = parseIntArgs(src, "width:", "height:", "time:");
				level.setWidth(intArgs[0]);
				level.setHeight(intArgs[1]);
				if(intArgs[2] >= 80) level.setTime(intArgs[2]);
			} else if(type.contains("camera-")){
				//make sure the camera is created last (so the CameraFollow can be setup automatically)
				if(i == objsrc.size() - 1) {
					createCamera(type, src);
				}
				else if(!camsFound){ //only the first camera is accepted
					camsFound = true;
					objsrc.add(src); //add camera data to end (so it is evaluated last)
					continue;
				}else {
					log.add("Warning: Another camera was declared before \"" + type + "\", therefore it has been ignored");
				}
			} else {
				log.add("Warning: \"" + type + "\" is not a valid type, therefore it has been ignored");
			}
		}
	}
	
	private void createCamera(String type, String src) {
		int intArgs[] = parseIntArgs(src, "x:", "y:");
		double speed = parseDoubleArgs(src, "speed:")[0];
		if(type.equals("camera-follow")) {
			//find player object
			Player p = null;
			for(GameObject o : objects) {
				if(o instanceof Player) p = (Player) o;
			}
			
			level.setCamera(new CameraFollow(
								p, 
								new Vec4(intArgs[0], intArgs[1], level.getWidth(), level.getHeight()),
								new Vec2(Engine.IMAGE_WIDTH, Engine.IMAGE_HEIGHT)));
		}  else if(type.equals("camera-scroll")) {
			level.setCamera(new CameraScroll(
								new Vec4(intArgs[0], intArgs[1], level.getWidth(), level.getHeight()),
								new Vec2(Engine.IMAGE_WIDTH, Engine.IMAGE_HEIGHT),
								speed));
		} else if(type.equals("camera-static")) {
			level.setCamera(new CameraStatic(new Vec2(intArgs[0], intArgs[1])));
		} else {
			log.add("Warning: \"" + type + "\" is not a valid type, therefore it has been ignored");
		}
	}

	public List<GameObject> getGameObjects(){
		return objects;
	}
	
	public boolean isLogsEmpty() {
		return log.isEmpty();
	}
	
	public List<String> getLogs(){
		return log;
	}
	
	public void clearLogs() {
		log.clear();
	}
	
	public String getLevelName() {
		return filepath.substring(filepath.lastIndexOf('\\') + 1, filepath.lastIndexOf('.'));
	}
	
	public int getTimeElapsedMs() {
		return (int) (timeElapsed / 1000000);
	}
	
	public static final int[] parseIntArgs(String src, String... params) {
		int args[] = new int[params.length];
		for(int i = 0; i < params.length; i++) {
			int pos = src.indexOf(params[i]);
			if(pos != -1) {
				String str = src.substring(pos + params[i].length(), src.indexOf(';', pos)).trim();
				args[i] = Integer.parseInt(str);
			}
		}
		return args;
	}
	
	public static final double[] parseDoubleArgs(String src, String... params) {
		double args[] = new double[params.length];
		for(int i = 0; i < params.length; i++) {
			int pos = src.indexOf(params[i]);
			if(pos != -1) {
				String str = src.substring(pos + params[i].length(), src.indexOf(';', pos)).trim();
				args[i] = Double.parseDouble(str);
			}
		}
		return args;
	}
	
	public static boolean[] parseBoolArgs(String src, String... params) {
		boolean args[] = new boolean[params.length];
		for(int i = 0; i < params.length; i++) {
			int pos = src.indexOf(params[i]);
			if(pos != -1) {
				String str = src.substring(pos + params[i].length(), src.indexOf(';', pos)).trim();
				args[i] = Boolean.parseBoolean(str);
			}
		}
		return args;
	}
	
	public static String[] parseStringArgs(String src, String...params) {
		String args[] = new String[params.length];
		//loop through each param to see if it exits, if so assign the corresponding variable
		for(int i = 0; i < params.length; i++) {
			int pos = src.indexOf(params[i]);
			if(pos != -1) {
				String str = src.substring(pos + params[i].length(), src.indexOf(';', pos)).trim();
				args[i] = str;
			}
		}
		return args;
	}
	
	private static Comparator<GameObject> sortByZ = new Comparator<GameObject>() {
		public int compare(GameObject g1, GameObject g2) {
			if(g1.getZ() < g2.getZ()) return -1;
			return 1;
		}
	};
}
