package gameobjects.tiles;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import static maths.Maths.*;

public class Block extends Tile {

	private static final ColourSet[] cols = {
			new ColourSet(0xff9C4A00, 0xffFFCEC6, 0xff9C4A00, 0xffFFCEC6),
			new ColourSet(0xff9C4A00, 0xffFFCEC6, 0, 0),
			new ColourSet(0xff9C4A00, 0, 0, 0xffFFCEC6),
			new ColourSet(0xff9C4A00, 0, 0xff9C4A00, 0)
	};
	
	public Block(int x, int y) {
		this(x, y, 1, 1);
	}
	
	public Block(int x, int y, int repeatWidth, int repeatHeight) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		this.repeatHeight = min(repeatHeight, 1);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:", "repeat-height:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new Block(intArgs[0], intArgs[1], intArgs[2], intArgs[3]));
	}

	public void render(Screen screen) {
		screen.renderRepeatingSpaced((int)x, (int)y, 64, 0, 8, 8, cols[0], repeatWidth, repeatHeight, 8, 8);
		screen.renderRepeatingSpacedFlipped((int)x + 8, (int)y, 64, 0, 8, 8, cols[1], repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeatingSpacedFlipped((int)x, (int)y + 8, 64, 0, 8, 8, cols[2], repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_VERTICAL);
		screen.renderRepeatingSpacedFlipped((int)x + 8, (int)y + 8, 64, 0, 8, 8, cols[3], repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_BOTH);
		
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
