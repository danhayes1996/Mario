package gameobjects.entities.items;

import gameobjects.entities.Entity;
import gameobjects.entities.mobs.Player;

public abstract class Item extends Entity{
	
	protected int score, time;
	
	public abstract void collect(Player p);
	
	public void release(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		time = 35;
	}

	public int getScore() {
		return score;
	}
}
