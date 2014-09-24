package cz.sam.cubix.render;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import cz.sam.cubix.Cubix;
import cz.sam.cubix.log.Log;
import cz.sam.cubix.render.animation.RotationAnimation;
import cz.sam.cubix.util.Vector3f;

public class EntityRender {
	
	public Cubix cubix;
	private boolean cameraGrabbed = false;
	private Vector3f cameraLocation = new Vector3f(Vector3f.ZERO);
	private Vector3f cameraClickLocation = new Vector3f(Vector3f.ZERO);
	
	RotationAnimation vortex;
	
	public EntityRender(Cubix cubix) {
		this.cubix = cubix;
		this.vortex = new RotationAnimation(10, 10, 50, 50, this.cubix.textureManager.getTexture("vortex"));
	}

	public void updateEntityRenderer(int delta) {
		ScaledResolution scaledResolution = new ScaledResolution(this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
		int mX = Mouse.getX() * scaledResolution.getScaledWidth() / this.cubix.SCREEN_WIDTH;
		int mY = scaledResolution.getScaledHeight() - Mouse.getY() * scaledResolution.getScaledHeight() / this.cubix.SCREEN_HEIGHT - 1;
		
		/*GL11.glViewport(0, 0, this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();*/
        this.setupOverlayRendering();
        
        if(this.cubix.world != null) {
        	if(this.cubix.world.isGenrated()) {
        		GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glEnable(GL11.GL_TEXTURE_2D);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        		this.updateCameraRotator();
            	this.renderWorld();
            }
        }
        
        if(this.cubix.guiScreen != null) {
        	this.cubix.guiScreen.handleInput();
        	if(this.cubix.guiScreen != null) {
        		this.cubix.guiScreen.preDrawScreen(mX, mY, delta);
        	}
        }
        
        if(!this.cubix.gameSettings.drawDefaultCursor) {
        	this.drawMouseCursor(mX, mY);
        }
	}
	
	private void updateCameraRotator() {
		int mouseX = Mouse.getX();
		int mouseY = this.cubix.SCREEN_HEIGHT - Mouse.getY();
		while(Mouse.next()) {
			if(Mouse.getEventButton() == 1) {
				if(Mouse.getEventButtonState()) {
					this.cameraGrabbed = true;
					this.cameraClickLocation.setX(mouseX);
					this.cameraClickLocation.setY(mouseY);
				} else {
					this.cameraGrabbed = false;
				}
			}
		}
		
		if(this.cameraGrabbed) {
			this.cameraLocation.addX((this.cameraClickLocation.getX() - mouseX) * 0.05F);
			this.cameraLocation.addY((this.cameraClickLocation.getY() - mouseY) * 0.05F);
		}
		
		
	}

	private void renderWorld() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		this.cubix.textureManager.bindTexture("grass2");
		Tessellator render = Tessellator.instance;
		render.startDrawingQuads();
		
		render.addVertexWithUV(0, 0, 0, 0, 0);
		render.addVertexWithUV(0, 20, 0, 0, 1);
		render.addVertexWithUV(20, 20, 0, 1, 1);
		render.addVertexWithUV(20, 0, 0, 1, 0);
		
		render.draw();
		
		int[][] map = this.cubix.world.getMap();
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[0].length; y++) {
				int blockSize = 5;
				int xPos = (int) (this.cameraLocation.getX() + (x * blockSize));
				int yPos = (int) (this.cameraLocation.getY() + (y * blockSize));
				
				
				if(map[x][y] == 0) {
					continue;
				}
				
				this.cubix.textureManager.bindTexture("grass2");
				
				render.startDrawingQuads();
				
				render.addVertexWithUV(xPos, yPos, 0, 0, 0);
				render.addVertexWithUV(xPos, yPos + blockSize, 0, 0, 1);
				render.addVertexWithUV(xPos + blockSize, yPos + blockSize, 0, 1, 1);
				render.addVertexWithUV(xPos + blockSize, yPos, 0, 1, 0);
				
				render.draw();
				
			}
		}
		
		float sX = (this.cameraLocation.getX() * 0.8F) + 100;
		float sY = (this.cameraLocation.getY() * 0.8F) + 100;
		
		this.vortex.setX(sX + 50);
		this.vortex.setY(sY + 50);
		
		this.vortex.draw();
		
		float bX = (this.cameraLocation.getX() * 0.5F) + 100;
		float bY = (this.cameraLocation.getY() * 0.5F) + 100;
		
		this.cubix.textureManager.bindTexture("sun2");
		render.startDrawingQuads();
		
		render.addVertexWithUV(bX, bY, 0, 0, 0);
		render.addVertexWithUV(bX, bY + 30, 0, 0, 1);
		render.addVertexWithUV(bX + 30, bY + 30, 0, 1, 1);
		render.addVertexWithUV(bX + 30, bY, 0, 1, 0);
		
		render.draw();
	}

	private void drawMouseCursor(int mX, int mY) {
		int x = mX;
		int y = mY;
		
		this.cubix.textureManager.bindTexture("gui_cursor");
		int width = 16;
		int height = 16;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x, y, 0, 0, 0);
		tessellator.addVertexWithUV(x, y + height, 0, 0, 1);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1, 1);
		tessellator.addVertexWithUV(x + width, y, 0, 1, 0);
		tessellator.draw();
	}

	public void setupOverlayRendering() {
		ScaledResolution scaledResolution = new ScaledResolution(this.cubix.SCREEN_WIDTH, this.cubix.SCREEN_HEIGHT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0D, 0.1D, 2000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}
	
}
