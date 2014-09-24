package cz.sam.cubix.gui.screen;

import org.lwjgl.opengl.Display;

import cz.sam.cubix.gui.ComponentListener;
import cz.sam.cubix.gui.Gui;
import cz.sam.cubix.gui.GuiButton;
import cz.sam.cubix.gui.GuiButtonOnOff;
import cz.sam.cubix.gui.GuiComponent;
import cz.sam.cubix.gui.GuiComponentSorter;
import cz.sam.cubix.gui.GuiSlider;
import cz.sam.cubix.gui.GuiString;
import cz.sam.cubix.render.FontColor;
import cz.sam.cubix.render.FontRender;
import cz.sam.cubix.render.FontSize;
import cz.sam.cubix.util.CursorUtil;

public class GuiOptions extends Gui implements ComponentListener {
	
	private GuiComponentSorter compSorter = new GuiComponentSorter();
	private GuiComponentSorter compStringSorter = new GuiComponentSorter();
	private GuiButtonOnOff vsyncButton = new GuiButtonOnOff();
	private GuiButtonOnOff defaultCursorButton = new GuiButtonOnOff(100, this);
	private GuiSlider volumeMusicSlider = new GuiSlider();
	private GuiSlider volumeSoundSlider = new GuiSlider();
	private float lastMusicVolume;
	private float lastSoundVolume;
	private float soundVolume;
	
	private int saveSringCounter = 0;
	private String saveString = "";
	
	@Override
	public void guiOpened() {
		this.vsyncButton.setState(this.getCubix().gameSettings.vsyncEnabled);
		this.defaultCursorButton.setState(this.getCubix().gameSettings.drawDefaultCursor);
		this.volumeMusicSlider.setValue(this.cubix.gameSettings.musicVolume);
		this.volumeSoundSlider.setValue(this.cubix.gameSettings.soundVolume);
		this.lastMusicVolume = this.cubix.gameSettings.musicVolume;
		this.lastSoundVolume = this.cubix.gameSettings.soundVolume;
		this.soundVolume = this.lastSoundVolume;
		this.volumeMusicSlider.setDrawPercentage(true);
		this.volumeSoundSlider.setDrawPercentage(true);
	}

	@Override
	public void initGui() {
		this.compSorter.clear();
		this.compSorter.setStartLocation(this.getWidth() / 2 + 50, 50);
		this.compSorter.setDistance(1);
		
		this.compSorter.addComponent(this.volumeMusicSlider);
		this.compSorter.addComponent(this.volumeSoundSlider);
		this.compSorter.addComponent(this.vsyncButton);
		this.compSorter.addComponent(this.defaultCursorButton);
		
		this.compSorter.sort(this);
		
		this.compStringSorter.clear();
		this.compStringSorter.setStartLocation(this.getWidth() / 2 - 200, 50);
		this.compStringSorter.setDistance(1);
		
		this.compStringSorter.addComponent(new GuiString("Music volume"));
		this.compStringSorter.addComponent(new GuiString("Sound volume"));
		this.compStringSorter.addComponent(new GuiString("Enable vertical synchronization"));
		this.compStringSorter.addComponent(new GuiString("Default cursor"));
		
		this.compStringSorter.sort(this);
		
		this.addComponent(new GuiButton(0, GuiButton.toCenter(this.getWidth() / 2), 200, "Save", this));
		this.addComponent(new GuiButton(1, GuiButton.toCenter(this.getWidth() / 2), 200 + 22, "Back to menu", this));
	}

	@Override
	public void drawScreen(int x, int y, float delta) {
		FontRender font = this.getCubix().fontRender;
		this.drawDefaultBackground();
		this.cubix.fontRender.setFontSize(FontSize.BIG_SIZE);
		this.drawCenteredString(font, "Game options", this.getWidth() / 2F, 20);
		
		FontColor.setColor(0, 170, 20);
		this.drawCenteredString(this.saveString, this.getWidth() / 2, 180);
		
		this.drawCopyright();
	}
	
	@Override
	public void updateScreen() {
		if(this.lastMusicVolume != this.volumeMusicSlider.getValue()) {
			this.cubix.soundManager.setMusicVolume(this.volumeMusicSlider.getValue());
			this.lastMusicVolume = this.volumeMusicSlider.getValue();
		}
		
		if(this.lastSoundVolume != this.volumeSoundSlider.getValue()) {
			this.cubix.gameSettings.soundVolume = this.volumeSoundSlider.getValue();
			this.lastSoundVolume = this.volumeSoundSlider.getValue();
		}
		
		if(!this.saveString.isEmpty()) {
			if(this.saveSringCounter > 40) {
				this.saveString = "";
				this.saveSringCounter = 0;
			}
			this.saveSringCounter++;
		}
	}

	@Override
	public void onGuiClosing() {
		this.cubix.soundManager.setMusicVolume(this.cubix.gameSettings.musicVolume);
		this.cubix.gameSettings.soundVolume = this.soundVolume;
	}

	@Override
	public void componentClicked(GuiComponent component) {
		if(component.getID() == 0) {
			this.getCubix().gameSettings.vsyncEnabled = this.vsyncButton.getState();
			Display.setVSyncEnabled(this.getCubix().gameSettings.vsyncEnabled);
			this.cubix.gameSettings.musicVolume = this.volumeMusicSlider.getValue();
			this.cubix.gameSettings.soundVolume = this.volumeSoundSlider.getValue();
			this.soundVolume = this.volumeSoundSlider.getValue();
			
			if(this.defaultCursorButton.getState()) {
				CursorUtil.setDefaultOSCursor();
				this.cubix.gameSettings.drawDefaultCursor = true;
			} else {
				CursorUtil.setBlancCursor();
				this.cubix.gameSettings.drawDefaultCursor = false;
			}
			
			this.saveString = "Settings saved !";
		}
		
		if(component.getID() == 1) {
			this.getCubix().displayGui(new GuiMainMenu());
		}
		
		if(component.getID() == 100) {
			if(this.defaultCursorButton.getState()) {
				CursorUtil.setDefaultOSCursor();
				this.cubix.gameSettings.drawDefaultCursor = true;
			} else {
				CursorUtil.setBlancCursor();
				this.cubix.gameSettings.drawDefaultCursor = false;
			}
		}
	}

}
