package maths;

public abstract class Maths {

	public static final int sign(double arg) {
		if(arg < 0) return -1;
		return 1;
	}
	
	public static final int min(int arg, int min) {
		return (arg < min) ? min : arg;
	}
	
	public static final int clamp(int arg, int min, int max) {
		if(arg < min) return min;
		if(arg > max) return max;
		return arg;
	}
	
	public static final double clamp(double arg, double min, double max) {
		if(arg < min) return min;
		if(arg > max) return max;
		return arg;
	}
	
	public static final int wrap(int arg, int min, int max) {
		if(arg < min) return max;
		if(arg > max) return min;
		return arg;
	}

	public static int biggest(int x, int y) {
		return (x < y) ? y : x;
	}

	public static int abs(int x) {
		return x < 0 ? x * -1 : x;
	}
}
