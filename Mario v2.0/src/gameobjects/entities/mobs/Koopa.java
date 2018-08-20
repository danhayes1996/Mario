package gameobjects.entities.mobs;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class Koopa extends Mob {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0xff109400, 0xffE79C21);
	
	private double speed = 0.5D, shellSpeed = 2D;
	private boolean isShell;
	
	public Koopa(int x, int y) {
		this.x = x;
		this.y = y;
		width = 16;
		height = 24;
		
		isShell = false;
	}
	
	
	public static List<GameObject> createFromString(String src){
		int intArgs[] = LevelCreator.parseIntArgs(src, "x:", "y:");
		return Arrays.asList(new Koopa(intArgs[0], intArgs[1]));
	}
	
	
	public void tick() {
		if(!isShell && velx == 0)
			velx = level.getPlayer().getX() > x ? speed : -speed;
		
		removeIfBelowLevel();
		
		//TODO: if isShell && velx == 0 && time has passed then Koopa comes out of shell
		
		vely += GRAVITY;
		
		x += velx;
		y += vely;
		
		collide();
		
		anim++;
	}

	public void render(Screen screen) {
		if(isShell) {
			screen.render((int)x, (int)y, 120, 80, width / 2, height, owCols);
			screen.renderFlipped((int)x + 8, (int)y, 120, 80, width / 2, height, owCols, Bitmap.FLIP_HORIZONTAL);
		} else {
			screen.renderFlipped((int)x, (int)y, (anim % 60 / 30) * 16, 120, width, height, owCols, (velx > 0) ?  Bitmap.FLIP_HORIZONTAL : Bitmap.FLIP_NONE);
		}
		
		if(Engine.DEBUG_MODE) {
			screen.renderHitbox(getBoundsTop());
			screen.renderHitbox(getBoundsBottom());
			screen.renderHitbox(getBoundsLeft());
			screen.renderHitbox(getBoundsRight());
		}
	}

	public void hit(GameObject o) {
		if(!isShell) {
			isShell =  true;
			velx = 0;
			y += 8; //move shell to ground
			height = 16;
		} else {
			if(velx == 0) {
				if(o != null && o.getX() > x) velx = -shellSpeed;
				else velx = shellSpeed;
			}
			else velx = 0;
		}
		if(o != null && o instanceof Player) o.setVelY(-3);
	}
	
	public boolean isInShell() {
		return isShell;
	}
	
	@Override
	public Rectangle getBoundsTop() {
		return new Rectangle((int) x + 4, (int) y, width - 8, 4);
	}
}
