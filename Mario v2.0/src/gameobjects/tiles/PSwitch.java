package gameobjects.tiles;

import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;

public class PSwitch extends Tile {
	
	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xffffffff, 0xff0677F9, 0xff0400BA);
	
	private static final int PSWITCH_TIME = 10 * Engine.TPS;
	
	private static int timer = 0; //static so multiple p switches can interact together
	
	public PSwitch(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:"};
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new PSwitch(intArgs[0], intArgs[1]));
	}
	
	@Override
	public void tick() {
		if(timer > 0) timer--;
		
		if(!solid && timer == 0) {
			//stop sound effect
			remove();
		}
	}
	
	public void render(Screen screen) {
		if(visible)	screen.render((int)x, (int)y, 32, 32, width, height, owCols);
		
		if(Engine.DEBUG_MODE) screen.renderHitbox(getBounds());
	}

	public void hit() {
		System.out.println("Activate P-Switch");
		timer = PSWITCH_TIME;
		solid = visible = false;
	}

}
