package levels;

import gameobjects.entities.mobs.Player;
import gfx.ColourSet;
import gfx.Screen;

public class HUD {

	private static ColourSet cols = new ColourSet(ColourSet.TRANSPARENT, 0xffACACAC, 0, 0xffE79C21);
	
	private Level level;
	private String levelName;
	private Player p;
	
	public HUD(Level level, String levelName) {
		this.level = level;
		this.p = level.getPlayer();
		this.levelName = levelName;
		System.out.println(levelName);
	}
	
	public void tick() {
	}
	
	public void render(Screen screen) {
		screen.renderTextAbsolute("MARIO", 10, 2, 0xffffffff);
		screen.renderTextAbsolute(String.format("%06d", p.getScore()), 10, 10, 0xffffffff);

		screen.renderAbsolute(92, 10, 96, 8, 8, 8, cols);
		screen.renderTextAbsolute("*" + String.format("%02d", p.getCoins()), 100, 10, 0xffffffff);
		
		screen.renderTextAbsolute("WORLD", 170, 2, 0xffffffff);
		screen.renderTextAbsolute(levelName, 178, 10, 0xffffffff);
		
		screen.renderTextAbsolute("TIME", 260, 2, 0xffffffff);
		screen.renderTextAbsolute(String.format("%3d", level.getTime()) , 268, 10, 0xffffffff);
	}
}
