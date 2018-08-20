package gfx;

import java.awt.Rectangle;

public class Screen extends Bitmap {

	public static final SpriteSheet sheet = new SpriteSheet("/textures/sprites.png");
	
	private int xOff, yOff;
	
	public Screen(int width, int height, int[] pixels) {
		super(width, height, pixels);
	}
	
	public Screen(int width, int height, int colour) {
		super(width, height, colour);
	}
	
	public void clear() {
		clear(0);
	}
	
	public void clear(int colour) {
		for(int i = 0; i < pixels.length; i++) pixels[i] = colour;
	}
	
	public void render(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours) {
		renderFlipped(x, y, sheetx, sheety, width, height, colours, FLIP_NONE);
	}
	
	public void renderAbsolute(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours) {
		renderFlipped(x + xOff, y + yOff, sheetx, sheety, width, height, colours, FLIP_NONE);
	}
	
	public void renderFlipped(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int flip) {
		x -= xOff;
		y -= yOff;
		for(int yy = 0; yy < height; yy++) {
			int ya = y + yy;
			if(ya < 0) continue;
			if(ya >= this.height) break;
			int yp = ((flip & FLIP_VERTICAL) == FLIP_VERTICAL) ? height - yy - 1 : yy;
			for(int xx = 0; xx < width; xx++) {
				int xa = x + xx;
				if(xa < 0) continue;
				if(xa >= this.width) break;
				int xp = ((flip & FLIP_HORIZONTAL) == FLIP_HORIZONTAL) ? width - xx - 1 : xx;
				int col = colours.getColour(Math.abs((sheet.pixels[(sheetx + xp) + (sheety + yp) * sheet.width] + 1) % 4));
				if(col != ColourSet.TRANSPARENT) pixels[xa + ya * this.width] = col;
			}
		}
	}
	
	public void renderRotated(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int rotate) {
		renderRotatedRepeating(x, y, sheetx, sheety, width, height, colours, rotate, 1);
	}
	
	public void renderRotatedRepeating(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int rotate, int repeat) {
		switch(rotate) {
			case ROTATE_NONE : renderRepeating(x, y, sheetx, sheety, width, height, colours, 1, repeat); break;
			case ROTATE_90   : renderRotated90(x - xOff, y - yOff, sheetx, sheety, width, height, colours, repeat);    break;
			case ROTATE_180  : renderRotated180(x - xOff, y - yOff, sheetx, sheety, width, height, colours, repeat);   break;
			case ROTATE_270  : renderRotated270(x - xOff, y - yOff, sheetx, sheety, width, height, colours, repeat);   break;
		}
	}
	
	private void renderRotated90(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeat) {
		for(int yy = 0; yy < height; yy++) {
			for(int xx = 0; xx < width; xx++) {
				int xa = x + height - 1 - yy;
				int ya = y + xx;
				int col = colours.getColour(Math.abs((sheet.pixels[(sheetx + xx) + (sheety + yy) * sheet.width] + 1) % 4));
				if(col != ColourSet.TRANSPARENT) {
					for(int i = 0; i < repeat; i++) {
						int xp = xa + i * height;
						if(xp < 0 || xp >= this.width) continue;
						pixels[xp + ya * this.width] = col;
					}
				}
			}
		}
	}
	
	private void renderRotated180(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeat) {
		for(int yy = 0; yy < height; yy++) {
			int ya = height - 1 - yy;
			if(ya < 0) continue;
			if(ya >= this.height) break;
			for(int xx = 0; xx < width; xx++) {
				int xa = x + width - 1 - xx;
				if(xa < 0 || xa >= this.width) continue;
				int col = colours.getColour(Math.abs((sheet.pixels[(sheetx + xx) + (sheety + yy) * sheet.width] + 1) % 4));
				if(col != ColourSet.TRANSPARENT) {
					for(int i = 0; i < repeat; i++) {
						int yp = (y + ya + (i * height));
						if(yp < 0 || yp >= this.height) continue;
						pixels[xa + yp * this.width] = col;
					}
				}
			}
		}
	}
	
	private void renderRotated270(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeat) {
		for(int yy = 0; yy < height; yy++) {
			for(int xx = 0; xx < width; xx++) {
				int xa = x + height - 1 - yy;
				int ya = y + width - 1 - xx;
				int col = colours.getColour(Math.abs((sheet.pixels[(sheetx + xx) + (sheety + yy) * sheet.width] + 1) % 4));
				if(col != ColourSet.TRANSPARENT) {
					for(int i = 0; i < repeat; i++) {
						int xp = xa + i * height;
						if(xp < 0 || xp >= this.width) continue;
						pixels[xp + ya * this.width] = col;
					}
				}
			}
		}
	}
	
	public void renderRepeating(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeatW, int repeatH) {
		renderRepeatingSpacedFlipped(x, y, sheetx, sheety, width, height, colours, repeatW, repeatH, 0, 0, FLIP_NONE);
	}
	
	public void renderRepeatingFlipped(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeatW, int repeatH, int flip) {
		renderRepeatingSpacedFlipped(x, y, sheetx, sheety, width, height, colours, repeatW, repeatH, 0, 0, flip);
	}
	
	public void renderRepeatingSpaced(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeatW, int repeatH, int spaceW, int spaceH) {
		renderRepeatingSpacedFlipped(x, y, sheetx, sheety, width, height, colours, repeatW, repeatH, spaceW, spaceH, FLIP_NONE);
	}
	
	public void renderRepeatingSpacedFlipped(int x, int y, int sheetx, int sheety, int width, int height, ColourSet colours, int repeatW, int repeatH, int spaceW, int spaceH, int flip) {
		for(int yy = 0; yy < repeatH; yy++) {
			int ya = y + (yy * height) + (yy * spaceH);
			for(int xx = 0; xx < repeatW; xx++) {
				int xa = x + (xx * width) + (xx * spaceW);
				renderFlipped(xa, ya, sheetx, sheety, width, height, colours, flip);
			}
		}
	}
	
	public void renderText(String text, int x, int y, int color) {
		Text.draw(this, text, x, y, color);
	}

	public void renderTextAbsolute(String text, int x, int y, int color) {
		Text.draw(this, text, x + xOff, y + yOff, color);
	}
	
	public void renderHitbox(Rectangle r) {
		renderHitbox(r, 0xff00ff00);
	}
	
	public void renderHitbox(Rectangle r, int colour) {
		for(int y = 0; y < r.getHeight(); y++) {
			int ya = y + (int)r.getY() - yOff;
			if(ya < 0) continue;
			if(ya >= height) break;
			for(int x = 0; x < r.getWidth(); x++) {
				int xa = x + (int)r.getX() - xOff;
				if(xa < 0) continue;
				if(xa >= width) break;
				if(x == 0 || x == r.getWidth() - 1 || y == 0 || y == r.getHeight() -1) 
					pixels[xa + ya * width] = colour;
			}
		}
	}

	public void renderRectangle(int x, int y, int width, int height, int colour) {
		x -= xOff;
		y -= yOff;
		for(int yy = 0; yy < height; yy++) {
			int ya = y + yy;
			if(ya < 0) continue;
			if(ya >= this.height) break;
			for(int xx = 0; xx < width; xx++) {
				int xa = x + xx;
				if(xa < 0) continue;
				if(xa >= this.width) break;
				pixels[xa + ya * this.width] = colour;
			}
		}
	}
	
	public void renderRectangleOutline(int x, int y, int width, int height, int colour) {
		x -= xOff;
		y -= yOff;
		for(int i = 0; i < width; i++) {
			if(x + i < 0 || x + i >= this.width) continue;
			if(y < 0 || y >= this.height) continue;
			pixels[x + i + y * this.width] = colour;
			pixels[x + i + (y + height) * this.width] = colour;
		}
		
		for(int i = 0; i <= height; i++) {
			pixels[x + (y + i) * this.width] = colour;
			pixels[x + width + (y + i) * this.width] = colour;
		}
	}
	
	public void setOffsets(int xOff, int yOff) {
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public int getXOffset() {
		return xOff;
	}
	
	public int getYOffset() {
		return yOff;
	}

	public void renderBitmap(int x, int y, Bitmap bitmap) {
		x -= xOff;
		y -= yOff;
		for(int yy = 0; yy < bitmap.height; yy++) {
			int ya = yy + y;
			if(ya < 0) continue;
			if(ya >= this.height) break;
			for(int xx = 0; xx < bitmap.width; xx++) {
				int xa = xx + x;
				if(xa < 0) continue;
				if(xa >= this.width) break;
				pixels[xa + ya * this.width] = bitmap.pixels[xx + yy * bitmap.width];
			}
		}
	}
}
