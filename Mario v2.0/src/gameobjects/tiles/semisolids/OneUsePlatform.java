package gameobjects.tiles.semisolids;

import java.util.Arrays;
import java.util.List;

import gameobjects.GameObject;
import gfx.Screen;
import levels.LevelCreator;

//can only be touched once before falling
public class OneUsePlatform extends MovingPlatform {

	public OneUsePlatform(int x, int y) {
		super(x, y);
	}
	
	public OneUsePlatform(int x, int y, int repeatWidth) {
		super(x, y, repeatWidth);
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:", "repeat-width:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new OneUsePlatform(intArgs[0], intArgs[1], intArgs[2]));
	}
	
	public void tick() {
		//if Entity.getBoundsBot() collides with this.getBounds() then solid = true
		//if above line = true and Entity = Player then fall
	}

	public void render(Screen screen) {
		super.render(screen);
	}

}
