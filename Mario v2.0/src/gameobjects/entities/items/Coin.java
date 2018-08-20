package gameobjects.entities.items;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.mobs.Player;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import sound.SoundEffect;

public class Coin extends Item {
	
	 /* replace with this for more efficient system for colour selection
	    a = 230, 156, 33
		b = 156, 74, 0 (-74, -82, -33)
		c = 82, 33, 0 (-74, -41, 0)

		sequence = a, b, c, b
		ticks_seperator = amount of ticks before changing animation
		
		r = 230 - (74 * (ticks % ([0, 1, 2] * ticks_seperator))
		g = 156 - (
		b =  33 - (clamp(33 * ticks % ([0, 1, 2] * ticks_seperator))
	 */
	
	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xff007B8C, 0xff9C4A00, 0xffE79C21);
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
		width = 10;
		height = 16;
		score = 100;
	}
	
	public static List<GameObject> createFromString(String src){
		int intArgs[] = LevelCreator.parseIntArgs(src, "x:", "y:");
		return Arrays.asList(new Coin(intArgs[0], intArgs[1]));
	}
	
	public void tick() {
		
	}

	public void render(Screen screen) {
		screen.render((int)x, (int)y, 72, 48, width, height / 2, owCols);
		screen.renderFlipped((int)x, (int)y + 8, 72, 48, width, height / 2, owCols, Bitmap.FLIP_VERTICAL);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
	
	public void collect(Player p) {
		p.addScore(score);
		p.addCoins(1);
		remove();
		SoundEffect.COLLECT_COIN.play();
	}
}
