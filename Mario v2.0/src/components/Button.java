package components;

import gfx.Screen;

public class Button extends Component {

	private String text;
	
	public Button(String text) {
		this(0, 0, text);
	}
	
	public Button(int x, int y, String text) {
		this(x, y, (text.length() + 2) * 8, 16, text);
	}
	
	public Button(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public void tick() {
		if(isClicked()) {
			if(clickEvent != null) clickEvent.click(this);
		}
	}

	public void render(Screen screen) {
		//render button
		screen.renderRectangle(x, y, width, height, 0xff888888);
		screen.renderRectangleOutline(x, y, width, height, 0);

		//render text
		int newx = x + (width / 2) - (text.length() * 8 / 2); 
		int newy = y + (height / 2) - 2;
		screen.renderText(text, newx, newy, 0xffffffff);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
