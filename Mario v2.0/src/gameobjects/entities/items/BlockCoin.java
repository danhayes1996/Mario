package gameobjects.entities.items;

import engine.Engine;
import gameobjects.entities.mobs.Player;
import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;
import sound.SoundEffect;

public class BlockCoin extends Item {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0xffB53121, 0xffE79C21);
	
	public BlockCoin() {
		this(0, 0);
	}
	
	public BlockCoin(int x, int y) {
		this.x = x;
		this.y = y;
		width = 8;
		score = 200;
		vely = -4;
		solid = false;
		
	}
	
	public void tick(){
		if(time == 35) SoundEffect.COLLECT_COIN.play();
		if(--time <= 0) remove();
		y += (vely += 0.2);
	}

	public void collect(Player p) {
	}

	public void render(Screen screen) {
		screen.render((int)x, (int)y, 72 + ((time / 7 % 3) * 8), 56, width, height / 2, owCols);
		screen.renderFlipped((int)x, (int)y + 8, 72 + ((time / 7 % 3) * 8), 56, width, height / 2, owCols, Bitmap.FLIP_VERTICAL);

		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}
}
