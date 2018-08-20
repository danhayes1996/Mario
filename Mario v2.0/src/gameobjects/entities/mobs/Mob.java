package gameobjects.entities.mobs;

import gameobjects.GameObject;
import gameobjects.entities.Entity;

public abstract class Mob extends Entity {
	
	protected static final int MAX_DISTANCE_FROM_MARIO = 16 * 16;
	protected int anim = 0;
	
	/**
	 * 
	 * @param e The Entity that hit this Mob.
	 */
	public abstract void hit(GameObject o);
}