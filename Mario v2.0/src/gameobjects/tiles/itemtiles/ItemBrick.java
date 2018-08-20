package gameobjects.tiles.itemtiles;

import static maths.Maths.min;

import java.util.ArrayList;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.items.BlockCoin;
import gameobjects.entities.items.Coin;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class ItemBrick extends ItemTile {

	private static final ColourSet top = new ColourSet(0, 0xffFFCEC6, 0xffFFCEC6, 0xff9C4A00);
	private static final ColourSet bot = new ColourSet(0, 0, 0xff9C4A00, 0xff9C4A00);
	
	public ItemBrick(int x, int y) {
		super(x, y);
	}
	
	public ItemBrick(int x, int y, Class<? extends GameObject> stored) {
		super(x, y, stored);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		
		Class<? extends GameObject> cItem = LevelCreator.CLASS_MAP.get(LevelCreator.parseStringArgs(src, "stored:")[0]); 
		if(cItem != null && cItem.equals(Coin.class)) cItem = BlockCoin.class;
		
		int repeatW = min(intArgs[2], 1);
		List<GameObject> objs = new ArrayList<>();
		for(int i = 0; i < repeatW; i++) {
			objs.add(new ItemBrick(intArgs[0] + (i * 16), intArgs[1], cItem));
		}
		return objs;
	}
	
	public void render(Screen screen) {
		screen.renderRepeating((int)x, (int)y, 64, 8, 8, 8, top, 2, 1);
		screen.renderRepeating((int)x, (int)y + 8, 64, 8, 8, 8, bot, 2, 1);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

}
