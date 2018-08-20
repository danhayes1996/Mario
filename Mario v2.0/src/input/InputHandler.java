package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener{

	private boolean[] keys;
	private boolean[] btnPress;
	private boolean[] btnClick;
	private int mx, my;
	
	public InputHandler() {
		keys = new boolean[65536];
		btnPress = new boolean[8];
		btnClick = new boolean[8];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void mousePressed(MouseEvent e) {
		btnPress[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		btnClick[e.getButton()] = btnPress[e.getButton()] = false;
	}
	
	public void mouseClicked(MouseEvent e) {
		btnClick[e.getButton()] = true;
	}
	
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
	
	public void absorbClick(int buttonCode) {
		if(buttonCode < btnPress.length) btnClick[buttonCode] = false;
	}

	public boolean isKeyPressed(int keyCode){
		if(keyCode >= keys.length) return false;
		return keys[keyCode];
	}
	
	public boolean isMouseButtonPressed(int buttonCode) {
		if(buttonCode >= btnPress.length) return false;
		return btnPress[buttonCode];
	}
	
	public boolean isMouseButtonClicked(int buttonCode) {
		if(buttonCode >= btnClick.length) return false;
		return btnClick[buttonCode];
	}
	
	public int getMouseX() {
		return mx;
	}
	
	public int getMouseY() {
		return my;
	}
	
	public Point getMousePoint() {
		return new Point(mx, (my / 3) * 3);
	}

	//unused
	public void keyTyped(KeyEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
