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

public class EmptyBlock extends Tile{

	public static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0, 0xff9C4A00);
	
	public EmptyBlock(int x, int y) {
		this(x, y, 1, 1);
	}
	
	public EmptyBlock(int x, int y, int repeatWidth, int repeatHeight) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		this.repeatHeight = min(repeatHeight, 1);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:", "repeat-height:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new EmptyBlock(intArgs[0], intArgs[1], intArgs[2], intArgs[3]));
	}

	public void render(Screen screen) {
		screen.renderRepeatingSpaced((int)x, (int)y, 72, 0, 8, 8, owCols, repeatWidth, repeatHeight, 8, 8);
		screen.renderRepeatingSpacedFlipped((int)x + 8, (int)y, 72, 0, 8, 8, owCols, repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeatingSpacedFlipped((int)x, (int)y + 8, 72, 0, 8, 8, owCols, repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_VERTICAL);
		screen.renderRepeatingSpacedFlipped((int)x + 8, (int)y + 8, 72, 0, 8, 8, owCols, repeatWidth, repeatHeight, 8, 8, Bitmap.FLIP_BOTH);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
