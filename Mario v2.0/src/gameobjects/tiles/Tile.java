package gameobjects.tiles;

import java.awt.Rectangle;

import gameobjects.GameObject;

public abstract class Tile extends GameObject {

	protected int repeatWidth = 1, repeatHeight = 1;
	
	public void tick() { }
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width * repeatWidth, height * repeatHeight);
	}
	
	@Override
	public int getWidth(){
		return width * repeatWidth;
	}
	
	@Override
	public int getHeight(){
		return height * repeatHeight;
	}
	
	public int getRepeatWidth() {
		return repeatWidth;
	}
	
	public int getRepeatHeight() {
		return repeatHeight;
	}
}
