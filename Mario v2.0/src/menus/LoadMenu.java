package menus;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.Button;
import components.Component;
import components.Textbox;
import engine.Engine;
import events.ClickEvent;
import gfx.Screen;

public class LoadMenu extends Menu {

	private Textbox txtBrowse;
	private Button btnBrowse, btnBack, btnLoad;
	
	private String selectedFile;
	
	public LoadMenu() {
		txtBrowse = new Textbox(65, 40, 100, 16);
		btnBrowse = new Button(170, 40, "Browse");
		btnBack = new Button(10, 135, 80, 20, "Back");
		btnLoad = new Button(Engine.IMAGE_WIDTH - 90, 135, 80, 20, "Load");
		btnLoad.setEnabled(false);
		
		assignHandlers();
	}
	
	private void assignHandlers() {
		btnBrowse.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("SMB Files", "smb");
		        fileChooser.setFileFilter(filter);
				
		        int result = fileChooser.showOpenDialog(Engine.instance);
				if(result == JFileChooser.APPROVE_OPTION) {
					btnLoad.setEnabled(true);
					selectedFile = fileChooser.getSelectedFile().getPath();
					txtBrowse.setText(selectedFile.substring(selectedFile.lastIndexOf('\\') + 1, selectedFile.lastIndexOf('.')));
					
				}
			}
		});
		
		btnBack.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				Engine.instance.setMenu(new MainMenu());
			}
		});
		
		btnLoad.setOnClickEventListener(new ClickEvent() {
			public void click(Component c) {
				Engine.instance.setMenu(null);
				Engine.instance.loadLevel(selectedFile);
			}
		});
	}

	public void tick() {
		txtBrowse.tick();
		btnBrowse.tick();
		btnBack.tick();
		btnLoad.tick();
	}

	public void render(Screen screen) {
		screen.clear(0xffDD2233);
		
		//render title
		screen.renderText("Load File", (Engine.IMAGE_WIDTH / 2) - (9 * 8) / 2, 20, 0xffffffff);
		
		//render components
		txtBrowse.render(screen);
		btnBrowse.render(screen);
		btnBack.render(screen);
		btnLoad.render(screen);
	}

}
