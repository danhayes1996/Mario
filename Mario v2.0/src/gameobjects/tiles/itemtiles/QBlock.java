package gameobjects.tiles.itemtiles;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.items.BlockCoin;
import gameobjects.entities.items.Coin;
import gameobjects.tiles.EmptyBlock;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import maths.Maths;

public class QBlock extends ItemTile {

	public static final Bitmap qblock = new Bitmap(16, 16, Screen.sheet.getPixels(48, 0, 16, 16));
	public static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0xff9C4A00, 0xffE79C21);
	
	public QBlock(int x, int y) {
		super(x, y);
	}
	
	public QBlock(int x, int y, Class<? extends GameObject> stored) {
		super(x, y, stored);
		remainingItems = Maths.clamp(remainingItems, 0, 1);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		
		Class<? extends GameObject> cItem = LevelCreator.CLASS_MAP.get(LevelCreator.parseStringArgs(src, "stored:")[0]); 
		if(cItem != null && cItem.equals(Coin.class)) cItem = BlockCoin.class;
		
		return Arrays.asList(new QBlock(intArgs[0], intArgs[1], cItem));
	}
	
	public void render(Screen screen) {
		if(!empty()) screen.render((int)x, (int)y, 48, 0, 16, 16, owCols);
		else {
			screen.render((int)x, (int)y, 72, 0, 8, 8, EmptyBlock.owCols);
			screen.renderFlipped((int)x + 8, (int)y, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_HORIZONTAL);
			screen.renderFlipped((int)x, (int)y + 8, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_VERTICAL);
			screen.renderFlipped((int)x + 8, (int)y + 8, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_BOTH);
		}
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
}
