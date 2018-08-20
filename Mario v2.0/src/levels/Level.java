package levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cameras.Camera;
import gameobjects.GameObject;
import gameobjects.entities.Entity;
import gameobjects.entities.mobs.Player;
import gameobjects.scenery.Scenery;
import gfx.Screen;

public class Level implements Iterable<GameObject> {

	private int width, height;
	private List<Scenery> scenery;
	private List<GameObject> objects;
	private Camera cam;
	private HUD hud;
	private int time;
	private Player player;
	
	public Level(String filepath) {
		System.out.println("Creating Level...\n");
		time = 500;
		LevelCreator lc = new LevelCreator(filepath, this);
		
		System.out.println("Level Created!");
		
		if(!lc.isLogsEmpty()) {
			System.out.println("\nLevel Creation Logs:");
			for(String l : lc.getLogs()) 
				System.out.println("\t" + l);
		}

		System.out.println("\nTime Elapsed: " + lc.getTimeElapsedMs() + "ms\n");

		objects = lc.getGameObjects();
		hud = new HUD(this, lc.getLevelName());
		
		scenery = new ArrayList<>();
	}
	
	int ticks = 0;
	public void tick() {
		if(++ticks % 60 == 0) time--;
		if(time == 0) player.kill();
		
		
		for(int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.tick();
			if(o instanceof Player && o.getX() < cam.getX()) {
				o.setX(cam.getX());
				o.setVelX(0);
			}
			
			if(o.isRemoved()) objects.remove(i);
		}
		cam.tick();
	}
	
	public void render(Screen screen) {
		screen.clear(0xff6B8CFF);
		screen.setOffsets((int)cam.getX(), (int)cam.getY());
		for(Scenery s : scenery)
			s.render(screen);
		for(GameObject o : objects)
			o.render(screen);
		hud.render(screen);
	}
	
	public Iterator<GameObject> iterator() {
		return objects.iterator();
	}
	
	public List<GameObject> getObjects(){
		return objects;
	}
	
	//stores based on object's z value
	public void addGameObject(GameObject obj) {
		if(obj instanceof Entity) ((Entity) obj).setLevel(this);
		
		int zIndex = 0;
		for(GameObject o : objects) {
			if(o.getZ() >= obj.getZ()) break;
			zIndex++;
		}
		objects.add(zIndex, obj);
	}

	public Player getPlayer() {
		if(player != null) return player;
		if(objects == null) return null;
		for(GameObject o : objects) {
			if(o instanceof Player) {
				player = (Player) o;
				return player;
			}
		}
		return null;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setCamera(Camera camera) {
		this.cam = camera;
	}
	
	public boolean isCameraSet() {
		return cam != null ? true : false;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
