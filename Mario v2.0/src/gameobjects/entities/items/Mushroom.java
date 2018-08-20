package gameobjects.entities.items;

public abstract class Mushroom extends Item {
	
	private double speed = 1;
	
	public Mushroom() {
		
	}
	
	public Mushroom(int x, int y) {
		this.x = x;
		this.y = y;
		this.velx = speed;
	}
	
	@Override
	public void release(int x, int y, int z) {
		super.release(x, y, z);
		time = 16;
	}
	
	public void tick() {
		//if Mushroom has been released from a block then do this.
		if(time > 0) {
			if(--time == 0) {
				velx = speed;
				vely = GRAVITY;
			}
			y--;
			return;
		}
		
		removeIfBelowLevel();
		
		vely += GRAVITY;
		
		x += velx;
		y += vely;
		collide();
	}
	
}
