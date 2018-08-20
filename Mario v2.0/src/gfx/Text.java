package gfx;

public class Text {

	private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@-*!";
	
	public static void draw(Screen screen, String text, int xp, int yp, int color) {
		xp -= screen.getXOffset();
		yp -= screen.getYOffset();
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++) {
			int charIndex = CHARS.indexOf(text.charAt(i));
			if(charIndex != -1) {
				int tx = (charIndex * 8) % Screen.sheet.width;
				int ty = ((charIndex * 8) / Screen.sheet.width) * 8 + 176;
				for(int y = 0; y < 8; y++) {
					int ya = y + yp;
					if(ya < 0) continue;
					if(ya >= screen.height) break;
					for(int x = 0; x < 8; x++) {
						int xa = xp + x + (i * 8);
						if(xa < 0) continue;
						if(xa >= screen.width) break;
						int col = Screen.sheet.pixels[(tx + x) + (ty + y) * Screen.sheet.width];
						if(col != ColourSet.TRANSPARENT) screen.pixels[xa + ya * screen.width] = color;
					}
				}
			}
		}
	}
	
}
