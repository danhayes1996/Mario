package gameobjects.scenery;

import gfx.Bitmap;
import gfx.ColourSet;
import gfx.Screen;

public class Mountain extends Scenery {

	Bitmap bTop = new Bitmap(96, 56, 8, 8, Screen.sheet);
	Bitmap bSide = new Bitmap(96, 48, 8, 8, Screen.sheet);
	Bitmap bEyes = new Bitmap(104, 56, 8, 8, Screen.sheet);
	
	ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0, 0xff00AD00, 0);
	
	public Mountain(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Screen screen) {
		//top area
	//	screen.renderBitmap((int)x + 32, (int)y - 32, bTop, owCols);
		//screen.renderBitmapFlipped((int)x + 40, (int)y - 32, bTop, owCols, Bitmap.FLIP_HORIZONTAL);
		
		//diagonal areas
		for(int i = 0; i < 4; i++) {
		//	screen.renderBitmap((int)x + (i * 8), (int)y - (i * 8), bSide, owCols);
		//	screen.renderBitmapFlipped((int)x + 72 - (i * 8), (int)y - (i * 8), bSide, owCols, Bitmap.FLIP_HORIZONTAL);
		}
		
		//solid green areas
		screen.renderRectangle((int)x + 8, (int)y, 64, 8, 0xff00AD00);
		screen.renderRectangle((int)x + 16, (int)y - 8, 48, 8, 0xff00AD00);
		screen.renderRectangle((int)x + 24, (int)y - 16, 32, 8, 0xff00AD00);
		screen.renderRectangle((int)x + 32, (int)y - 24, 16, 8, 0xff00AD00);
		
		//eye areas
		//screen.renderBitmap((int)x + 24, (int)y - 8, bEyes, owCols);
		//screen.renderBitmap((int)x + 48, (int)y - 8, bEyes, owCols);
		//screen.renderBitmap((int)x + 40, (int)y - 24, bEyes, owCols);
	}

}
