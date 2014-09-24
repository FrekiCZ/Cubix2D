package cz.sam.cubix.render.texture;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class Texture {
	
	private int id;
	private int width;
	private int height;
	private BufferedImage image;
	
	public Texture(int textureID, int width, int height) {
		this.id = textureID;
		this.width = width;
		this.height = height;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
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
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}
}
