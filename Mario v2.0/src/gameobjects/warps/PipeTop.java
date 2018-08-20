package gameobjects.warps;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.tiles.Pipe;
import gfx.Bitmap;
import gfx.Screen;
import levels.LevelCreator;

public class PipeTop extends WorpBlock {

	private Rectangle hitbox, warpHitbox;
	private int rotate;
	
	public PipeTop(int x, int y) {
		this(x, y, 0, null);
	}
	
	public PipeTop(int x, int y, int dir) {
		this(x, y, dir, null);
	}
	
	public PipeTop(int x, int y, int dir, PipeTop linkTo) {
		this.x = x;
		this.y = y;
		if(dir % 2 == 0) {
			width = 32;
			height = 16;
			if(dir == 0) {
				rotate = Bitmap.ROTATE_NONE;
				warpHitbox = new Rectangle((int)x + (width/2) - 8, (int) y - 1, 16, 2);
			} else {
				rotate = Bitmap.ROTATE_180;
				warpHitbox = new Rectangle((int)x + (width/2) - 8, (int) y + height, 16, 2);
			}
		}else {
			width = 16;
			height = 32;
			if(dir == 1) {
				rotate = Bitmap.ROTATE_90;
				warpHitbox = new Rectangle((int)x + width, (int) y + (height/2) - 8, 2, 16);
			}else {
				rotate = Bitmap.ROTATE_270;
				warpHitbox = new Rectangle((int)x - 1, (int) y + (height/2) - 8, 2, 16);
			}
		}
		hitbox = new Rectangle(x, y, width, height);
		if(linkTo != null) setLink(linkTo);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new PipeTop(intArgs[0], intArgs[1]));
	}

	public void render(Screen screen) {
		screen.renderRotated((int)x, (int)y, 0, 32, 32, 16, Pipe.owCols, rotate);

		if(Engine.DEBUG_MODE) {
			screen.renderHitbox(getBounds());
			screen.renderHitbox(getWorpBounds());
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return hitbox;
	}
	
	@Override
	public Rectangle getWorpBounds() {
		return warpHitbox;
	}
	
	@Override
	public String toString() {
		return "PipeTop:[x=" + x + ", y=" + y + ", link=PipeTop:[x=" + link.getX() + ", y=" + link.getY() + "]]";
	}
}
