package gameobjects.tiles.semisolids;

import java.awt.Rectangle;

import gameobjects.tiles.Tile;

public abstract class SemiSolidTile extends Tile {

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width * repeatWidth, 2);
	}
}
