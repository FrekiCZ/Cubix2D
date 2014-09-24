package cz.sam.cubix.gui.screen;

import cz.sam.cubix.gui.Gui;
import cz.sam.cubix.gui.GuiProgressBar;
import cz.sam.cubix.gui.IProgressUpdate;
import cz.sam.cubix.world.World;

public class GuiCreateWorld extends Gui implements IProgressUpdate {
	
	public GuiProgressBar progressBar = new GuiProgressBar();
	//private int status;
	
	@Override
	public void guiOpened() {
		this.cubix.world = new World();
		this.cubix.world.generate(this);
	}
	
	@Override
	public void initGui() {
		this.progressBar.setLocation(this.getWidth() / 2 - 250 / 2, 50);
		this.addComponent(this.progressBar);
	}
	
	@Override
	public void updateScreen() {
		/*this.progressBar.setValue(this.status);
		if(this.status > 100) {
			this.status = 0;
		}
		this.status++;*/
	}
	
	@Override
	public void intUpdate(int i) {
		this.progressBar.setValue(i);
		if(i == 100) {
			
			this.cubix.displayGui(null);
		}
	}

	@Override
	public void drawScreen(int x, int y, float delta) {
		this.drawDefaultBackground();
		//Color.setColor(255, 255, 255);
		this.drawRainbowCenteredString("Creating world...", this.getWidth() / 2, 20);
		//this.drawCenteredString("Creating world...", this.getWidth() / 2, 20);
	}

	@Override
	public void mouseClicked(int x, int y, int mouse_button) {
		
	}

	@Override
	public void mouseClickMove(int x, int y, int mouse_button, long click_time) {
		
	}

	@Override
	public void keyTyped(int key, char event_char) {
		
	}

	@Override
	public void onGuiClosing() {
		
	}

}
