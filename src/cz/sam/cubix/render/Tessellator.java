package cz.sam.cubix.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class Tessellator {
	
	public static Tessellator instance = new Tessellator();
	
	private int vertexCount;
	private int textureCount;
	private int[] vertexBuffer;
	private int[] textureBuffer;
	private float xOffset = 0F;
	private float yOffset = 0F;
	private float zOffset = 0F;
	private boolean isDrawing = false;
	private int drawMode;
	private Color color = new Color(0, 0, 0);
	private boolean useTextures;
	
	public void startDrawingQuads() {
		this.drawMode = GL11.GL_QUADS;
	}
	
	public void startDrawing(int mode) {
		this.drawMode = mode;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void addVertex(float x, float y, float z) {
		this.isDrawing = true;
		if(this.vertexBuffer == null) {
			this.vertexBuffer = new int[0x10000];
		}
		
		int vertexCount = this.vertexCount == 0 ? 0 : this.vertexCount;
		this.vertexCount += 3;
		
		this.vertexBuffer[vertexCount] = Float.floatToIntBits(x + this.xOffset);
		this.vertexBuffer[vertexCount + 1] = Float.floatToIntBits(y + this.yOffset);
		this.vertexBuffer[vertexCount + 2] = Float.floatToIntBits(z + this.zOffset);
	}
	
	public void addTextureUV(float u, float v) {
		this.useTextures = true;
		if(this.textureBuffer == null) {
			this.textureBuffer = new int[0x10000];
		}
		
		int textureCount = this.textureCount == 0 ? 0 : this.textureCount;
		this.textureCount += 2;
		
		this.textureBuffer[textureCount] = Float.floatToIntBits(u);
		this.textureBuffer[textureCount + 1] = Float.floatToIntBits(v);
	}
	
	public void addVertexWithUV(float x, float y, float z, float u, float v) {
		this.addVertex(x, y, z);
		this.addTextureUV(u, v);
	}
	
	public void setTranslation(float x, float y, float z) {
		this.xOffset = x;
		this.yOffset = y;
		this.zOffset = z;
	}
	
	public void addTranslation(float x, float y, float z) {
		this.xOffset += x;
		this.yOffset += y;
		this.zOffset += z;
	}
	
	public void draw() {
		if(this.isDrawing) {
			if(!this.useTextures) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f((float)this.color.getRed() / 255F, (float)this.color.getGreen() / 255F, (float)this.color.getBlue() / 255F, (float)this.color.getAlpha() / 255F);
			} else {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(1F, 1F, 1F);
			}
			
			this.drawObjects();
		} else {
			try {
				throw new Exception("Tessellator: not tessellating !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw(boolean default_tex_color) {
		if(this.isDrawing) {
			if(!this.useTextures) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f((float)this.color.getRed() / 255F, (float)this.color.getGreen() / 255F, (float)this.color.getBlue() / 255F, (float)this.color.getAlpha() / 255F);
			} else {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				if(default_tex_color) {
					GL11.glColor3f(1F, 1F, 1F);
				}
			}
			
			this.drawObjects();
		} else {
			try {
				throw new Exception("Tessellator: not tessellating !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void drawObjects() {
		GL11.glBegin(this.drawMode); {
			
			int vertexCount = this.vertexCount / 3;
			
			int texureCount = this.textureCount / 2;
			int tx = 0;
			for(int i = 0; i < vertexCount; ++i) {
				if(this.useTextures) {
					if(!(tx > texureCount)) {
						int floatIntU = this.textureBuffer[(tx * 2)];
						int floatIntV = this.textureBuffer[(tx * 2) + 1];
						float U = Float.intBitsToFloat(floatIntU);
						float V = Float.intBitsToFloat(floatIntV);
						GL11.glTexCoord2f(U, V);
					}
					tx++;
				}
				
				int floatIntX = this.vertexBuffer[i * 3];
				int floatIntY = this.vertexBuffer[i * 3 + 1];
				int floatIntZ = this.vertexBuffer[i * 3 + 2];
				float x = Float.intBitsToFloat(floatIntX);
				float y = Float.intBitsToFloat(floatIntY);
				float z = Float.intBitsToFloat(floatIntZ);
				GL11.glVertex3f(x, y, z);
			}
		} GL11.glEnd();
		this.reset();
	}
	
	private void reset() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.textureBuffer = null;
		this.vertexBuffer = null;
		this.color = new Color(0, 0, 0);
		this.vertexCount = 0;
		this.textureCount = 0;
		this.drawMode = 0;
		this.useTextures = false;
	}
	
	
}