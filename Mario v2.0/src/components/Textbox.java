package components;

import gfx.Screen;

public class Textbox extends Component {

	private String text;
	private Screen textScreen;

	public Textbox(int x, int y) {
		this(x, y, 70, 16);
	}
	
	public Textbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		text = "";
		textScreen = new Screen(width, height, 0xffffffff);
	}
	
	public void tick() {
	}

	public void render(Screen screen) {
		//render text to textbox
		textScreen.clear(0xffffffff);
		textScreen.renderText(text, 5, 6, 0);
		
		//render textbox to screen
		screen.renderBitmap(x, y, textScreen);
		screen.renderRectangleOutline(x, y, width, height, 0);
	}

	public void addChar(char c) {
		text += c;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
