package gameobjects.tiles.semisolids;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class BowserBridge extends SemiSolidTile {

	public static final Bitmap top = new Bitmap(8, 8, Screen.sheet.getPixels(48, 16, 8, 8));
	public static final Bitmap bot = Bitmap.flipBitmap(top, Bitmap.FLIP_VERTICAL);
	
	public static final ColourSet tCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0xff7B7B7B, 0xffFF3900);
	public static final ColourSet bCols = new ColourSet(ColourSet.TRANSPARENT, 0xffFF3900, 0xff7B7B7B, 0xffFF3900);
	
	public BowserBridge(int x, int y) {
		this.x = x;
		this.y = y;
		this.repeatWidth = 26;
		width = 8;
	}

	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new BowserBridge(intArgs[0], intArgs[1]));
	}
	
	public void render(Screen screen) {
		screen.renderRepeating((int)x, (int)y, 48, 16, 8, 8, tCols, repeatWidth, repeatHeight);
		screen.renderRepeatingFlipped((int)x, (int)y + 8, 48, 16, 8, 8, bCols, repeatWidth, repeatHeight, Bitmap.FLIP_VERTICAL);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
