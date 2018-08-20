package components;

import java.awt.event.MouseEvent;

import engine.Engine;
import events.ClickEvent;
import gfx.Screen;
import input.Point;

public abstract class Component {

	protected int x, y, width, height;
	protected boolean enabled = true;
	
	protected ClickEvent clickEvent;
	
	public abstract void tick();
	public abstract void render(Screen screen);
	
	protected boolean isClicked() {
		if(!enabled) return false;
		if(Engine.INPUT.isMouseButtonClicked(MouseEvent.BUTTON1)) {
			Point mousePos = Engine.INPUT.getMousePoint();
			if(mousePos.x >= x * 3 && mousePos.x <= (x * 3) + (width * 3) &&
					mousePos.y >= y * 3 && mousePos.y <= (y * 3) + (height * 3)) {
				Engine.INPUT.absorbClick(MouseEvent.BUTTON1);				
				return true;
			}
		}			
		return false;
	}
	
	public void setOnClickEventListener(ClickEvent event) {
		this.clickEvent = event;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
