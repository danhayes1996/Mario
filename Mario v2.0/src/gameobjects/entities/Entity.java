package gameobjects.entities;

import java.awt.Rectangle;

import gameobjects.GameObject;
import gameobjects.tiles.Tile;
import levels.Level;

public abstract class Entity extends GameObject {

	protected Level level;
	protected static final double GRAVITY =  0.15625D;
	
	public void collide(){
		if(!solid) return;
		for(GameObject o : level){
			if(o.isSolid() && o instanceof Tile && getBounds().intersects(o.getBounds())){
				Tile t = (Tile) o;
				if(getBoundsTop().intersects(t.getBounds())){
					vely = 0;
					y = t.getY() + t.getHeight();
				}
				if(getBoundsBottom().intersects(t.getBounds())){
					vely = 0;
					y = t.getY() - height;
				}
				if(getBoundsLeft().intersects(t.getBounds())){
					velx *= -1;
					x = t.getX() + t.getWidth();
				}
				if(getBoundsRight().intersects(t.getBounds())){
					velx *= -1;
					x = t.getX() - width;
				}
			}
		}
	}
	
	/**
	 * remove an Entity if it is below the level (fell off of the level)
	 * @return True if the Entity was removed, False otherwise.
	 */
	public boolean removeIfBelowLevel() {
		if(y > level.getHeight()) {
			remove();
			return true;
		}
		return false;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public Rectangle getBoundsTop(){
		return new Rectangle((int) x + 5, (int) y, width - 10, 4);
	}
	
	public Rectangle getBoundsBottom(){
		return new Rectangle((int) x + 5, (int) y + height - 4, width - 10, 4);
	}
	
	public Rectangle getBoundsLeft(){
		return new Rectangle((int) x, (int) y + 6, 4, height - 9);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle((int) x + width - 4, (int) y + 6, 4, height - 9);
	}
}
