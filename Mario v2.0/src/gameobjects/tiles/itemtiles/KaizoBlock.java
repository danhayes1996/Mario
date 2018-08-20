package gameobjects.tiles.itemtiles;

import static maths.Maths.min;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.Entity;
import gameobjects.entities.items.BlockCoin;
import gameobjects.entities.items.Coin;
import gameobjects.tiles.EmptyBlock;
import gfx.Bitmap;
import gfx.Screen;
import levels.LevelCreator;
import maths.Maths;

public class KaizoBlock extends ItemTile {

	public KaizoBlock(int x, int y, Class<? extends GameObject> stored) {
		super(x, y, stored);
		visible = false;
		remainingItems = Maths.clamp(remainingItems, 0, 1);
	}

	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		
		Class<? extends GameObject> cItem = LevelCreator.CLASS_MAP.get(LevelCreator.parseStringArgs(src, "stored:")[0]); 
		if(cItem != null && cItem.equals(Coin.class)) cItem = BlockCoin.class;
		
		int repeatW = min(intArgs[2], 1);
		List<GameObject> objs = new ArrayList<>();
		for(int i = 0; i < repeatW; i++) {
			objs.add(new KaizoBlock(intArgs[0] + (i * 16), intArgs[1], cItem));
		}
		return objs;
	}
	
	@Override
	public void tick() {
		if(!visible && level.getPlayer().getY() < y + height) solid = false;
		else solid = true;
		super.tick();
	}
	
	public void render(Screen screen) {
		if(visible) {
			screen.render((int)x, (int)y, 72, 0, 8, 8, EmptyBlock.owCols);
			screen.renderFlipped((int)x + 8, (int)y, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_HORIZONTAL);
			screen.renderFlipped((int)x, (int)y + 8, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_VERTICAL);
			screen.renderFlipped((int)x + 8, (int)y + 8, 72, 0, 8, 8, EmptyBlock.owCols, Bitmap.FLIP_BOTH);
		}
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
	
	@Override
	public void hit(Entity e) {
		super.hit(e);
		visible = true;
	}

	@Override
	public Rectangle getBounds() {
		return visible ? super.getBounds() : new Rectangle((int)x, (int)y + height - 4, width, 4);
	}
	
}
