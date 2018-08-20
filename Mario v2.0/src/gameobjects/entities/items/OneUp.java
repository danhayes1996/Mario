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

public class OneUp extends Mushroom {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffFFFFFF, 0xff109400, 0xffE79C21);
	
	public OneUp() {
		super();
	}
	
	public OneUp(int x, int y) {
		super(x, y);
		this.score = 0; //signal for oneup
	}

	public static List<GameObject> createFromString(String src){
		int intArgs[] = LevelCreator.parseIntArgs(src, "x:", "y:");
		return Arrays.asList(new OneUp(intArgs[0], intArgs[1]));
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
		p.addLife();
		remove();
		SoundEffect.COLLECT_1UP.play();
	}

}
