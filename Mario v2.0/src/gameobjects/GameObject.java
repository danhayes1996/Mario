package gameobjects;

import java.awt.Rectangle;

import gfx.Screen;

public abstract class GameObject implements Comparable<GameObject> {

	protected double x, y, velx, vely;
	protected int width = 16, height = 16, z = 0; //z = render depth
	protected boolean removed, moving, movable, solid = true, visible = true;
	
	public abstract void tick();
	public abstract void render(Screen screen);

	public int compareTo(GameObject other) {
		int result = Double.compare(x, other.x);
		if(result == 0) result = Double.compare(y, other.y);
		if(getBounds().intersects(other.getBounds())){
			result = Integer.compare(z, other.z);
		}
		return result;
	}
	
	protected static int getDirection(String direction) {
		if(direction == null) return 0;
		else if(direction.equals("left")) return 1;
		else if(direction.equals("down")) return 2;
		else if(direction.equals("right")) return 3;
		return 0;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX(){
		return x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY(){
		return y;
	}
	
	public void setVelX(double velx) {
		this.velx = velx;
	}
	
	public double getVelX(){
		return velx;
	}
	
	public void setVelY(double vely) {
		this.vely = vely;
	}
	
	public double getVelY(){
		return vely;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void remove(){
		System.out.println("removed: " + this);
		removed = true;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public boolean isMovable(){
		return movable;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName() + ":[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}