package cameras;

import maths.Vec2;
import maths.Vec4;

public abstract class Camera {
	
	protected double x, y;
	protected int xstart, ystart, xend, yend;
	protected int sWidth, sHeight;
	
	public Camera(Vec4 bounds, Vec2 screenDimensions) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.xstart = bounds.x;
		this.ystart = bounds.y;
		this.xend = bounds.z;
		this.yend = bounds.w;
		this.sWidth = screenDimensions.x;
		this.sHeight = screenDimensions.y;
	}
	
	public abstract void tick();
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
