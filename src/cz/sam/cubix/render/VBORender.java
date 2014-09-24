package cz.sam.cubix.render;

import java.awt.Color;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class VBORender {
	
	public static VBORender instance = new VBORender();
	private int drawMode;
	private int vertexCount;
	private int textureCount;
	private int[] vertexBuffer;
	private int[] textureBuffer;
	
	public VBORender() {
		
	}
	
	public void startDrawingQuads() {
		this.drawMode = GL11.GL_QUADS;
	}
	
	public void addVertex(float x, float y, float z) {
		if(this.vertexBuffer == null) {
			this.vertexBuffer = new int[0x10000];
		}
		
		int vertexCount = this.vertexCount == 0 ? 0 : this.vertexCount;
		this.vertexCount += 3;
		
		this.vertexBuffer[vertexCount] = Float.floatToIntBits(x);
		this.vertexBuffer[vertexCount + 1] = Float.floatToIntBits(y);
		this.vertexBuffer[vertexCount + 2] = Float.floatToIntBits(z);
	}
	
	public void addTextureUV(float u, float v) {
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
	
	public void draw() {
		int vertexCount = this.vertexCount / 3;
		int texureCount = this.textureCount / 2;
		
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertexCount * 3);
		FloatBuffer textureData = BufferUtils.createFloatBuffer(texureCount * 2);
		
		for(int i = 0; i < vertexCount; ++i) {
			int floatIntX = this.vertexBuffer[i * 3];
			int floatIntY = this.vertexBuffer[i * 3 + 1];
			int floatIntZ = this.vertexBuffer[i * 3 + 2];
			float x = Float.intBitsToFloat(floatIntX);
			float y = Float.intBitsToFloat(floatIntY);
			float z = Float.intBitsToFloat(floatIntZ);
			vertexData.put(new float[] { x, y, z });
		}
		
		int tx = 0;
		for(int i = 0; i < vertexCount; ++i) {
			if(!(tx > texureCount)) {
				int floatIntU = this.textureBuffer[(tx * 2)];
				int floatIntV = this.textureBuffer[(tx * 2) + 1];
				float U = Float.intBitsToFloat(floatIntU);
				float V = Float.intBitsToFloat(floatIntV);
				textureData.put(new float[] { U, V });
			}
			tx++;
		}
		
		vertexData.flip();
		textureData.flip();
		
		int vboVertexHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
        int vbotextureHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbotextureHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_STATIC_DRAW);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
        
        GL11.glColor3f(1, 1, 1);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDrawArrays(this.drawMode, 0, vertexCount);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        this.reset();
	}
	
	private void reset() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.textureBuffer = null;
		this.vertexBuffer = null;
		this.vertexCount = 0;
		this.textureCount = 0;
		this.drawMode = GL11.GL_QUADS;
	}
	
}
