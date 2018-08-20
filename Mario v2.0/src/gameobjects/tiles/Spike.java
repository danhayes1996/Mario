package gameobjects.tiles;

import static maths.Maths.min;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class Spike extends Tile {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0xffffffff, 0);
	
	public Spike(int x, int y) {
		this(x, y, 1, 1);
	}
	
	public Spike(int x, int y, int repeatWidth, int repeatHeight) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		this.repeatHeight = min(repeatHeight, 1);
	}

	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:", "repeat-height:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new Spike(intArgs[0], intArgs[1], intArgs[2], intArgs[3]));
	}
	
	public void render(Screen screen) {
		screen.renderRepeating((int)x, (int)y, 136, 0, width, height, owCols, repeatWidth, repeatHeight);
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
