package gameobjects.tiles;

import static maths.Maths.min;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class Ground extends Tile{

	private static final ColourSet owCols = new ColourSet(0, 0, 0xffFFCEC6, 0xff9C4A00);
	
	public Ground(int x, int y) {
		this(x, y, 1, 1);
	}
	
	public Ground(int x, int y, int repeatWidth, int repeatHeight) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		this.repeatHeight = min(repeatHeight, 1);
	}

	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:", "repeat-height:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new Ground(intArgs[0], intArgs[1], intArgs[2], intArgs[3]));
	}
	
	public void render(Screen screen) {
		screen.renderRepeating((int)x, (int)y, 32, 0, width, height, owCols, repeatWidth, repeatHeight);
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
	
}
