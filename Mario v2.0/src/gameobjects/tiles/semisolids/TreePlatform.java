package gameobjects.tiles.semisolids;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import static maths.Maths.*;

public class TreePlatform extends SemiSolidTile {

	public static final Bitmap tstart = new Bitmap(8, 16, Screen.sheet.getPixels(64, 16, 8, 16));
	public static final Bitmap tend = Bitmap.flipBitmap(tstart, Bitmap.FLIP_HORIZONTAL);
	public static final Bitmap tmid = new Bitmap(8, 16, Screen.sheet.getPixels(72, 16, 8, 16));
	public static final Bitmap wood = new Bitmap(8, 8, Screen.sheet.getPixels(56, 16, 8, 8));
	
	public static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffE75A10, 0xffBDFF18, 0);
	
	public TreePlatform(int x, int y) {
		this(x, y, 8);
	}
	
	public TreePlatform(int x, int y, int repeatWidth) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		width = 8;
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new TreePlatform(intArgs[0], intArgs[1], intArgs[2]));
	}

	public void render(Screen screen) {
		//tree trunk
		screen.renderRepeating((int)x + 8, (int)y + 16, 56, 16, 8, 8, owCols, repeatWidth - 2, (int)((screen.getHeight() - y) / 8) - 1);
		//tree top
		screen.render((int)x, (int)y, 64, 16, 8, 16, owCols);
		screen.renderFlipped((int)x + (width*repeatWidth) - 8, (int)y, 64, 16, 8, 16, owCols, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeating((int)x + 8, (int)y, 72, 16, 8, 16, owCols, repeatWidth - 2, repeatHeight);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
