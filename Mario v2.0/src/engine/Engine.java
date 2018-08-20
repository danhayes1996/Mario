package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.UIManager;

import gameobjects.entities.mobs.Player;
import gfx.Screen;
import input.InputHandler;
import levels.Level;
import menus.MainMenu;
import menus.Menu;
import sound.SoundEffect;
import sound.SoundEffect.Volume;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static Engine instance;
	
	public static boolean DEBUG_MODE = true;
	private static Player DEBUG_player;
	private static int DEBUG_FPS;
	
	public static final int TPS = 60;
	public static final int IMAGE_WIDTH = 300, IMAGE_HEIGHT = IMAGE_WIDTH / 16 * 9, IMAGE_SCALE = 3;
	private static final int WIDTH = IMAGE_WIDTH * IMAGE_SCALE, HEIGHT = IMAGE_HEIGHT * IMAGE_SCALE;
	private static final String TITLE = "SMB1 ATTEMPT";
	
	private boolean running = false;
	private Thread thread;
	
	private int[] pixels;
	private BufferedImage image;
	
	public static final InputHandler INPUT = new InputHandler();
	private Screen screen;
	private Menu menu;
	private Level level;
	
	public Engine() {
		instance = this;
		
		image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		initWindow();
		
		screen = new Screen(IMAGE_WIDTH, IMAGE_HEIGHT, pixels);
		menu = new MainMenu();
		
		SoundEffect.setVolume(Volume.ON);
		
		addKeyListener(INPUT);
		addMouseListener(INPUT);
		addMouseMotionListener(INPUT);
			
		start();
	}
	
	private void initWindow() {
		Dimension d = new Dimension(WIDTH, HEIGHT);
		JFrame f = new JFrame(TITLE);
		f.getContentPane().setPreferredSize(d);
		f.getContentPane().setMinimumSize(d);
		f.getContentPane().setMaximumSize(d);
		
		f.setVisible(true);
		f.setResizable(false);
		f.add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setCursor(f.getToolkit().createCustomCursor(new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ), new Point(), null));
		
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this, "GameThread");
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running) return;
		try {
			running = false;
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void tick() {
		if(menu == null) level.tick();
		else menu.tick();
		INPUT.absorbClick(MouseEvent.BUTTON1); // absorb any clicks that haven't been used.
	}
	
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		if(menu == null) level.render(screen);
		else menu.render(screen);
		
		input.Point p = INPUT.getMousePoint();
		screen.renderRectangle(p.x / IMAGE_SCALE, p.y / IMAGE_SCALE, 1, 1, 0xff00ff00);
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
				
		//render debug info
		if(DEBUG_MODE) {
			//rectangle
			g.setColor(new Color(0, 0, 0, 154));
			g.fillRect(1, 2, 200, 100);
			
			//text settings
			g.setFont(new Font("consolas", Font.PLAIN, 15));
			int y = g.getFontMetrics().getHeight();
			g.setColor(Color.WHITE);
			
			//global text
			g.drawString("FPS: " + DEBUG_FPS, 4, y);
			
			//mario text
			if(DEBUG_player != null) {
				g.drawString("Mario", 4, y += g.getFontMetrics().getHeight());
				g.drawString("x: " + String.format("%.2f", DEBUG_player.getX()) + ", y: " + String.format("%.2f", DEBUG_player.getY()), 4, y += g.getFontMetrics().getHeight());
				g.drawString("velx: " + String.format("%.2f", DEBUG_player.getVelX()) + ", vely: " + String.format("%.2f", DEBUG_player.getVelY()), 4, y += g.getFontMetrics().getHeight());
				g.drawString("state: " + DEBUG_player.getState(), 4, y += g.getFontMetrics().getHeight());
			}
		}
		g.dispose();
		bs.show();
	}

	double delta_test = 0;
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double ns = 1000000000D / TPS;
		int ticks = 0;
		int frames = 0;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;	
			lastTime = now;
			while(delta >= 1){
				tick();
				ticks++;
				delta--;
			}
			
			if(running) render();
			frames++;
	
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;

				double mem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576D;
				System.out.printf("%d FRAMES, %d TICKS, %.2fMB USED\n", frames, ticks, mem);
				
				DEBUG_FPS = frames;

				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}
	
	public void loadLevel(String path) {
		level = new Level(path);
		DEBUG_player = level.getPlayer();
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public static void main(String[] args) {
		new Engine();
	}
}
