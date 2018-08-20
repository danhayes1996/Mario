package gfx;

public class ColourSet {

	public static final int TRANSPARENT = 0xffff00ff;
	
	private int[] colours;
	
	public ColourSet(int col1, int col2, int col3, int col4) {
		colours = new int[]{ col1, col2, col3, col4 };
	}
	
	public void setColour(int index, int colour) {
		colours[index] = colour;
	}
	
	public int getColour(int index) {
		return colours[index];
	}
	
}
