package gameobjects.tiles.semisolids;

import static maths.Maths.min;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

//moves statically in a direction forever
public class MovingPlatform extends SemiSolidTile{

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0xffFF3603, 0xffFEB667);
	
	public MovingPlatform(int x, int y) {
		this(x, y, 8);
	}
	
	public MovingPlatform(int x, int y, int repeatWidth) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		width = height = 8;
	}

	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new MovingPlatform(intArgs[0], intArgs[1], intArgs[2]));
	}
	
	public void render(Screen screen) {
		screen.renderRepeating((int)x, (int)y, 40, 16, width, height, owCols, repeatWidth, repeatHeight);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
