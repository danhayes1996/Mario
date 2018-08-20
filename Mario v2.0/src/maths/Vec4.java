package maths;

public class Vec4 {
	
	public final int x, y, z, w;
	
	public Vec4(int vals) {
		x = y = z = w = vals;
	}
	
	public Vec4(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
}
