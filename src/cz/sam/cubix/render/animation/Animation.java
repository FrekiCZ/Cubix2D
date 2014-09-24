package cz.sam.cubix.render.animation;

import cz.sam.cubix.render.texture.Texture;

public abstract class Animation {
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected Texture texture;
	
	public abstract void draw();

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
