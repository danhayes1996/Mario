package maths;

public class Vec2 {

	public final int x, y;
	
	public Vec2() {
		this(0);
	}

	public Vec2(int vals) {
		x = y = vals;
	}
	
	public Vec2(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
