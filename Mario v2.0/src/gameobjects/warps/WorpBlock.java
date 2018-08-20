package gameobjects.warps;

import java.awt.Rectangle;

import gameobjects.tiles.Tile;

public abstract class WorpBlock extends Tile{

	protected WorpBlock link;
	
	public void setLink(WorpBlock linkTo) {
		this.link = linkTo;
		linkTo.link = this;
	}
	
	public boolean hasWarpLink() {
		return link != null;
	}
	
	public Rectangle getWorpBounds() {
		return new Rectangle((int)x + (width/2) - 8, (int) y - 1, 16, 2);
	}
}
