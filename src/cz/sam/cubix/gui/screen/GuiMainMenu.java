package cz.sam.cubix.gui.screen;

import cz.sam.cubix.gui.ComponentListener;
import cz.sam.cubix.gui.Gui;
import cz.sam.cubix.gui.GuiButton;
import cz.sam.cubix.gui.GuiComponent;
import cz.sam.cubix.gui.GuiComponentSorter;

public class GuiMainMenu extends Gui implements ComponentListener {
	
	private GuiComponentSorter compSorter = new GuiComponentSorter();
	
	@Override
	public void guiOpened() {
		
	}
	
	@Override
	public void initGui() {
		this.compSorter.clear();
		this.compSorter.setStartLocation(this.getWidth() / 2 - GuiButton.WIDTH / 2, this.getHeight() / 3);
		this.compSorter.setDistance(3);
		
		this.compSorter.addComponent(new GuiButton(0, "Play new game", this));
		this.compSorter.addComponent(new GuiButton(1, "Load game", this));
		this.compSorter.addComponent(new GuiButton(2, "Play multiplayer", this).setEnabled(false));
		this.compSorter.addComponent(new GuiButton(3, "Game Options", this));
		this.compSorter.addComponent(new GuiButton(4, "Exit game", this));
		
		this.compSorter.sort(this);
	}

	@Override
	public void drawScreen(int x, int y, float delta) {
		this.drawDefaultBackground();
		this.drawLogo();
		this.drawCopyright();
		this.cubix.fontRender.drawRandomRainbowString(5, 5, false, 10);
		this.cubix.fontRender.drawRandomRainbowString(5, 15, true, 10);
	}

	private void drawLogo() {
		this.getCubix().textureManager.bindTexture("cubix_logo");
		this.drawTextureQuad(this.getWidth() / 2 - 100 / 2, this.getHeight() / 3 - 85, 100, 55, 0, 0, 1, 1);
	}

	@Override
	public void onGuiClosing() {
		
	}

	@Override
	public void componentClicked(GuiComponent component) {
		if(component.getID() == 0) {
			this.getCubix().displayGui(new GuiCreateWorld());
		}
		
		if(component.getID() == 1) {
			
		}
		
		if(component.getID() == 2) {
			
		}
		
		if(component.getID() == 3) {
			this.getCubix().displayGui(new GuiOptions());
		}
		
		if(component.getID() == 4) {
			this.onGuiClosing();
			this.getCubix().shutdown();
		}
	}

}
