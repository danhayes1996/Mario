package gameobjects.entities.mobs;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import maths.Maths;
import sound.SoundEffect;

public class Goomba extends Mob {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffFFC8B8, 0, 0xff9C4A00);

	private boolean xFlip = false;
	private double speed = 0.5;
	
	public Goomba(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static List<GameObject> createFromString(String src){
		int intArgs[] = LevelCreator.parseIntArgs(src, "x:", "y:");
		return Arrays.asList(new Goomba(intArgs[0], intArgs[1]));
	}
	
	public void tick() {
		//if dead and despawn time not expired
		if(!solid) {
			if(--anim == 0) remove();
			else return;
		}
		
		removeIfBelowLevel();
		
		if(Maths.abs((int)(level.getPlayer().getX() - x)) >= MAX_DISTANCE_FROM_MARIO) return;
		
		if(solid && anim++ >= 15) {
			anim = 0;
			xFlip = !xFlip;
		}
		
		if(velx == 0 && solid) 
			velx = level.getPlayer().getX() > x ? speed : -speed;
		
		vely += GRAVITY;
			
		x += velx;
		y += vely;
		
		collide();
	}

	public void render(Screen screen) {
		if(solid) { //if alive
			screen.render((int)x, (int)y, 80, 64, 8, 8, owCols);
			screen.renderFlipped((int)x + 8, (int)y, 80, 64, 8, 8, owCols, Bitmap.FLIP_HORIZONTAL);
			screen.renderFlipped((int)x, (int)y + 8, 80, 72, 16, 8, owCols, xFlip ? Bitmap.FLIP_HORIZONTAL : 0);
		} else {
			screen.render((int)x, (int)y + 8, 88, 64, 8, 8, owCols);
			screen.renderFlipped((int)x + 8, (int)y + 8, 88, 64, 8, 8, owCols, Bitmap.FLIP_HORIZONTAL);
		}
		
		if(Engine.DEBUG_MODE) {
			screen.renderHitbox(getBoundsTop());
			screen.renderHitbox(getBoundsBottom());
			screen.renderHitbox(getBoundsLeft());
			screen.renderHitbox(getBoundsRight());
		}
	}
	
	public void hit(GameObject o) {
		solid = false;
		velx = 0;
		anim = 60;
		SoundEffect.STOMP.play();
		if(o instanceof Player) o.setVelY(-3);
	}
}
