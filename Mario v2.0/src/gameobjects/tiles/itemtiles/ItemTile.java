package gameobjects.tiles.itemtiles;

import java.awt.Rectangle;

import gameobjects.GameObject;
import gameobjects.entities.Entity;
import gameobjects.entities.items.BlockCoin;
import gameobjects.entities.items.Item;
import gameobjects.entities.mobs.Mob;
import gameobjects.tiles.Tile;
import levels.Level;

public abstract class ItemTile extends Tile {

	protected Level level;
	protected int remainingItems;
	protected int hitFrames, releaseFrames;
	
	protected Class<? extends GameObject> storedItem;
	
	public ItemTile(int x, int y) {
		this(x, y, null);
	}
	
	public ItemTile(int x, int y, Class<? extends GameObject> stored) {
		this.x = x;
		this.y = y;
		this.z = 8;
		this.storedItem = stored;
		if(storedItem != null) 
			remainingItems = storedItem.equals(BlockCoin.class) ? 5 : 1;
	}
	
	public void tick() {
		if(hitFrames > 9){
			y--;
			hitFrames--;
		}else if(hitFrames > 0){
			y++;
			hitFrames--;
		}
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public void hit(Entity e) {
		hitFrames = 18;
		if(storedItem != null && remainingItems > 0) {
			remainingItems--;
			try {
				Item item = (Item) storedItem.newInstance();
				item.release((int)(x + ((width - item.getWidth()) / 2)), (int)y, z - 1);
				level.addGameObject(item);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		//get bounds just above current position (to collide with objects just above this)
		Rectangle bounds = getBounds();
		bounds.translate(0, -3);
		
		//hit any mob objects that are above this tile
		for(GameObject obj : level.getObjects()) {
			if(obj instanceof Mob && obj.isSolid()) {
				Mob m = (Mob) obj;
				if(m.getBoundsBottom().intersects(bounds)) {
					m.hit(this);
				}
			}
		}
	}
	
	public boolean empty() {
		return remainingItems == 0;
	}
}
