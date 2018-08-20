package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import maths.Maths;

public class Bitmap {

	public static final int ROTATE_NONE = 0, ROTATE_90 = 1, ROTATE_180 = 2, ROTATE_270 = 3;
	public static final int FLIP_NONE = 0, FLIP_VERTICAL = 1, FLIP_HORIZONTAL = 2, FLIP_BOTH = 3;
	
	protected int[] pixels;
	protected int width, height;
	
	public Bitmap(String filepath) {
		try {
			BufferedImage image = ImageIO.read(Bitmap.class.getResource(filepath));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public Bitmap(int x, int y, SpriteSheet sheet) {
		this(x, y, 16, 16, sheet);
	}
	
	public Bitmap(int x, int y, int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		pixels = sheet.getPixels(x, y, width, height);
	}

	public Bitmap(int width, int height, int colour) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height];
		for(int i = 0; i < width*height; i++) pixels[i] = colour;
	}
	
	/**
	 * Adds a bitmap above the caller of this function.
	 */
	public Bitmap addBitmapAbove(Bitmap other) {
		int w = Maths.biggest(this.width, other.width);
		int h = this.height + other.height;
		int[] px = new int[w * h];
		//top part
		for(int y = 0; y < other.height; y++) {
			for(int x = 0; x < w; x++) {
				if(x >= other.width) px[x + y * w] = ColourSet.TRANSPARENT;
				else px[x + y * w] = other.pixels[x + y * other.width];
			}
		}
		//bottom part
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < w; x++) {
				if(x > this.width) px[x + y * w] = ColourSet.TRANSPARENT;
				else {
					System.out.println(x + ", " + y);
					px[x + (y + other.height) * w] = this.pixels[x + y * this.width];
				}
			}
		}
		return new Bitmap(w, h, px);
	}
	
	public Bitmap addBitmapBelow(Bitmap other) {
		return other.addBitmapAbove(this);
	}
	
	public Bitmap addBitmapLeft(Bitmap other) {
		int w = this.width + other.width;
		int h = Maths.biggest(this.height, other.height);
		int[] px = new int[w * h];
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				if(x >= other.width) {
					if(y < this.height) px[x + y * w] = this.pixels[(x - other.width) + y * this.width];
					else px[x + y * w] = ColourSet.TRANSPARENT;
				}
				else if(y < other.height) px[x + y * w] = other.pixels[x + y * other.width];
				else px[x + y * w] = ColourSet.TRANSPARENT;
			}
		}
		
		return new Bitmap(w, h, px);
	}
	
	public Bitmap addBitmapRight(Bitmap other) {
		return other.addBitmapLeft(this);
	}
	
	public static final Bitmap flipBitmap(Bitmap bitmap, int flip) {
		int[] px = new int[bitmap.getSize()];
		for(int y = 0; y < bitmap.height; y++) {
			int yp = ((flip & FLIP_VERTICAL) == FLIP_VERTICAL) ? bitmap.height - y - 1 : y;
			for(int x = 0; x < bitmap.width; x++) {
				int xp = ((flip & FLIP_HORIZONTAL) == FLIP_HORIZONTAL) ? bitmap.width - x - 1 : x;
				px[xp + yp * bitmap.width] = bitmap.pixels[x + y * bitmap.width];
			}
		}
		return new Bitmap(bitmap.width, bitmap.height, px);
	}

	public static final Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
		if(rotation == ROTATE_90) return rotateBitmap90(bitmap);
		else if (rotation == ROTATE_180) return rotateBitmap180(bitmap);
		else if (rotation == ROTATE_270) return rotateBitmap270(bitmap);
		else return bitmap;
	}
	
	private static Bitmap rotateBitmap90(Bitmap bitmap) {
		int[] px = new int[bitmap.getSize()];
		for(int i = 0; i < px.length; i++) px[i] = 0xffff0000;
		for(int y = 0; y < bitmap.height; y++) {
			for(int x = 0; x < bitmap.width; x++) {
				int xx = bitmap.height - 1 - y;
				int yy = x;
				px[xx + yy * bitmap.height] = bitmap.pixels[x + y * bitmap.width];
			}
		}
		return new Bitmap(bitmap.height, bitmap.width, px);
	}

	private static Bitmap rotateBitmap180(Bitmap bitmap) {
		return rotateBitmap90(rotateBitmap90(bitmap));
	}

	private static Bitmap rotateBitmap270(Bitmap bitmap) {
		return rotateBitmap90(rotateBitmap90(rotateBitmap90(bitmap)));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getSize() {
		return width*height;
	}

	public int[] getPixels(int x, int y, int width, int height) {
		int[] px = new int[width * height];
		for(int yy = 0; yy < height; yy++) {
			int ya = y + yy;
			if(ya < 0) continue;
			if(ya >= this.height) break;
			for(int xx = 0; xx < width; xx++) {
				int xa = x + xx;
				if(xa < 0) continue;
				if(xa >= this.width) break;
				px[xx + yy * width] = pixels[xa + ya * this.width];
			}
		}
		return px;
	}
	
	public int getPixel(int x, int y) {
		return pixels[x + y * width];
	}
	
	public int getPixel(int index) {
		return pixels[index];
	}
}
