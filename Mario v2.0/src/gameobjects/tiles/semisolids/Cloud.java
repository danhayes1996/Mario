package gameobjects.tiles.semisolids;

import java.util.ArrayList;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import static maths.Maths.*;

public class Cloud extends SemiSolidTile {

	public static final Bitmap tleft = new Bitmap(8, 8, Screen.sheet.getPixels(16, 72, 8, 8));
	public static final Bitmap tright = Bitmap.flipBitmap(tleft, Bitmap.FLIP_HORIZONTAL);
	public static final Bitmap bright = new Bitmap(8, 8, Screen.sheet.getPixels(24, 72, 8, 8));
	public static final Bitmap bleft = Bitmap.flipBitmap(bright, Bitmap.FLIP_HORIZONTAL);
	
	public static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0, 0);
	
	public Cloud(int x, int y) {
		this(x, y, 1);
	}

	public Cloud(int x, int y, int repeatWidth) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		width = height = 16;
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:", "repeat-height:"};
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		List<GameObject> objs = new ArrayList<>();

		int repeatH = min(intArgs[3], 1);
		for(int i = 0; i < repeatH; i++) {
			objs.add(new Cloud(intArgs[0], (16*i) + intArgs[1], intArgs[2]));
		}
		return objs;
	}
	
	public void render(Screen screen) {
		screen.renderRepeatingSpaced((int)x, (int)y, 24, 56, 8, 8, owCols, repeatWidth, repeatHeight, 8, 0);
		screen.renderRepeatingSpacedFlipped((int)x + 8, (int)y, 24, 56, 8, 8, owCols, repeatWidth, repeatHeight, 8, 0, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeatingSpacedFlipped((int)x, (int)y + 8, 24, 72, 8, 8, owCols, repeatWidth, repeatHeight, 8, 0, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeatingSpaced((int)x + 8, (int)y + 8, 24, 72, 8, 8, owCols, repeatWidth, repeatHeight, 8, 0);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
