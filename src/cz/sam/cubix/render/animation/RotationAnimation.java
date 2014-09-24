package cz.sam.cubix.render.animation;

import org.lwjgl.opengl.GL11;

import cz.sam.cubix.render.Tessellator;
import cz.sam.cubix.render.texture.Texture;

public class RotationAnimation extends Animation {
	
	private int speed = 10;
	private float angle = 0;
	
	public RotationAnimation(float x, float y, float width, float height, Texture texture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
	
	@Override
	public void draw() {
		this.update();
		GL11.glPushMatrix();
		
		GL11.glTranslatef(this.x + this.width / 2, this.y + this.height / 2, 0);
		GL11.glRotatef(this.angle, 0, 0, 1F);
		GL11.glTranslatef(-(this.x + this.width / 2), -(this.y + this.height / 2), 0);
		
		this.texture.bind();
		Tessellator tessllator = Tessellator.instance;
		tessllator.startDrawingQuads();
		tessllator.addVertexWithUV(this.x, this.y, 0, 0, 0);
		tessllator.addVertexWithUV(this.x, this.y + this.width, 0, 0, 1);
		tessllator.addVertexWithUV(this.x + this.width, this.y + this.width, 0, 1, 1);
		tessllator.addVertexWithUV(this.x + this.width, this.y, 0, 1, 0);
		tessllator.draw();
		
		GL11.glPopMatrix();
	}
	
	private void update() {
		this.angle++;
		if(this.angle > 360) {
			this.angle = 0;
		}
	}
	
}
