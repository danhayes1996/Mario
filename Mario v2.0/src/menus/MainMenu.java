package menus;

import components.Button;
import components.Component;
import engine.Engine;
import events.ClickEvent;
import gfx.Screen;

public class MainMenu extends Menu {

	private Button btnLoad, btnOptions, btnQuit;
	
	public MainMenu() {
		int x = (Engine.IMAGE_WIDTH / 2) - 40;
		btnLoad = new Button(x, 50, 80, 20, "Load File");
		btnOptions = new Button(x, 80, 80, 20, "Options");
		btnQuit = new Button(x, 110, 80, 20, "Quit");
		
		assignHandlers();
	}
	
	private void assignHandlers() {
		btnLoad.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				Engine.instance.setMenu(new LoadMenu());
			}
		});
		
		btnOptions.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				System.out.println("Options Menu");
			}
		});
		
		btnQuit.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				System.exit(1);
			}
		});
	}

	public void tick() {
		btnLoad.tick();
		btnOptions.tick();
		btnQuit.tick();
	}

	public void render(Screen screen) {
		screen.clear(0xffDD2233);
		
		//render title
		screen.renderText("Mario Rip-off", (Engine.IMAGE_WIDTH / 2) - (13 * 8) / 2, 20, 0xffffffff);
		
		//render buttons
		btnLoad.render(screen);
		btnOptions.render(screen);
		btnQuit.render(screen);
	}

}
