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

public class Bridge extends SemiSolidTile {

	public static final ColourSet bCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0xffE75A10, 0xffF7D6B5);
	public static final ColourSet rCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0, 0xffBDFF18);
	
	public Bridge(int x, int y, int repeatWidth) {
		this.x = x;
		this.y = y;
		this.repeatWidth = min(repeatWidth, 1);
		width = height = 8;
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new Bridge(intArgs[0], intArgs[1], intArgs[2]));
	}

	public void render(Screen screen) {
		//rail
		screen.renderRepeating((int)x + 4, (int)y - 8, 56, 24, 8, 8, rCols, repeatWidth - 1, 1);
		//bridge
		screen.render((int)x, (int)y, 40, 24, 8, 8, bCols);
		screen.renderFlipped((int)x + (width*repeatWidth) - 8, (int)y, 40, 24, 8, 8, bCols, Bitmap.FLIP_HORIZONTAL);
		screen.renderRepeating((int)x + 8, (int)y, 48, 24, 8, 8, bCols, repeatWidth - 2, repeatHeight);
		
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
