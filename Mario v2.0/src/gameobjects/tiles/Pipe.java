package gameobjects.tiles;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.warps.PipeTop;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import maths.Maths;

import static maths.Maths.*;

public class Pipe extends Tile {

	public static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xff109400, 0xff8CD600, 0);
	
	private Rectangle hitbox;
	private int rotate;
	
	public Pipe(int x, int y) {
		this(x, y, 1);
	}
	
	public Pipe(int x, int y, int repeat) {
		this(x, y, repeat, 0);
	}

	public Pipe(int x, int y, int repeat, int dir) {
		this.x = x;
		this.y = y;
		
		dir = clamp(dir, 0, 3);
		if(dir % 2 == 1) {
			this.repeatWidth = repeat;
			width = 8;
			height = 28;
			if(dir == 1) rotate = Bitmap.ROTATE_90;
			else rotate = Bitmap.ROTATE_270;
			this.y+=2;
			hitbox = new Rectangle(x, (int)this.y, width * repeatWidth, height * repeatHeight);
		} else {
			this.repeatHeight = repeat;
			width = 28;
			height = 8;
			if(dir == 0) rotate = Bitmap.ROTATE_NONE;
			else rotate = Bitmap.ROTATE_180;
			this.x+=2;
			hitbox = new Rectangle((int)this.x, y, width * repeatWidth, height * repeatHeight);
		}
	}
	
	public static List<GameObject> createFromString(String src) {
		List<GameObject> objs = new ArrayList<>();
		
		//get link pipe data and changed source
		String[] res = findLinkData(src);
		String linkSrc = res[0];
		src = res[1];
		
		String[] intParams = { "x:", "y:", "repeat:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, intParams);
		String strArgs[] = LevelCreator.parseStringArgs(src, "direction:");
		
		//get direction as int
		int dir = getDirection(strArgs[0]);
		PipeTop pt = new PipeTop(intArgs[0], intArgs[1], dir);
		objs.add(pt);
		
		//get x,y start coords for the pipe
		int x = intArgs[0];
		int y = intArgs[1];
		if(dir == 0) y += pt.getHeight();
		else if(dir == 1) x -= 8 * intArgs[2];
		else if(dir == 2) y -= 8 * intArgs[2];
		else if(dir == 3) x += pt.getWidth();
		
		Pipe p = new Pipe(x, y, intArgs[2], dir);
		objs.add(p);
		
		//setup link pipe
		if(linkSrc != null) {
			int intLinkArgs[] = LevelCreator.parseIntArgs(linkSrc, intParams);
			String linkDirection = LevelCreator.parseStringArgs(linkSrc, "direction:")[0];
			int linkDir = getDirection(linkDirection);
			
			PipeTop linkPt = new PipeTop(intLinkArgs[0], intLinkArgs[1], linkDir);
			linkPt.setLink(pt);
			objs.add(linkPt);
			
			//get x,y start coords for the pipe
			int linkX = intLinkArgs[0];
			int linkY = intLinkArgs[1];
			if(linkDir == 0) linkY += linkPt.getHeight();
			else if(linkDir == 1) linkX -= 8 * intLinkArgs[2];
			else if(linkDir == 2) linkY -= 8 * intLinkArgs[2];
			else if(linkDir == 3) linkX += linkPt.getWidth();
			
			Pipe linkP = new Pipe(linkX, linkY, intLinkArgs[2], linkDir);
			objs.add(linkP);
		}
		
		return objs;
	}
	
	//returns the link data and the source string without the link data
	private static String[] findLinkData(String src) {
		String res = null;
		if(src.contains("link:")) {
			int start = src.indexOf("{", src.indexOf("link:")) + 1;
			int end = src.indexOf("}", start);
			res = src.substring(start, end).trim();
			
			//cut link data from src string
			src = src.substring(0, start) + src.substring(end);
		}
		return new String[]{res, src};
	}
	
	public void render(Screen screen) {
		screen.renderRotatedRepeating((int)x, (int)y, 0, 48, 28, 8, owCols, rotate, Maths.biggest(repeatWidth, repeatHeight));
		
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
	
	@Override
	public Rectangle getBounds() {
		return hitbox;
	}
}
