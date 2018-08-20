package gameobjects.entities.items;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.mobs.Player;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import sound.SoundEffect;

public class SuperMushroom extends Mushroom {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffFFFFFF, 0xffB13425, 0xffE79C21);
	
	public SuperMushroom() {
		super();
	}
	
	public SuperMushroom(int x, int y) {
		super(x, y);
		this.score = 1000;
	}

	public static List<GameObject> createFromString(String src){
		int intArgs[] = LevelCreator.parseIntArgs(src, "x:", "y:");
		return Arrays.asList(new SuperMushroom(intArgs[0], intArgs[1]));
	}
	
	public void render(Screen screen) {
		screen.render((int)x, (int)y, 80, 32, width, height, owCols);
		if(Engine.DEBUG_MODE) {
			screen.renderHitbox(getBoundsTop());
			screen.renderHitbox(getBoundsBottom());
			screen.renderHitbox(getBoundsLeft());
			screen.renderHitbox(getBoundsRight());
		}
	}

	public void collect(Player p) {
		p.addScore(score);
		p.setPowerUp(Player.POWERUP.MUSHROOM);
		remove();
		SoundEffect.COLLECT_POWERUP.play();
	}

}
