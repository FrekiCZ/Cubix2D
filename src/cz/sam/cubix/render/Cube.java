package cz.sam.cubix.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Cube {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int amountOfVertices = 4;
	private int vertexSize = 3;
	private int vboVertexHandle;
	
	public Cube(int x, int y, int width, int height) {
		FloatBuffer vertexData = BufferUtils.createFloatBuffer(amountOfVertices * vertexSize);
		vertexData.put(new float[]{
				x, y, 0,
				x, y + height, 0,
				x + width, y + height, 0,
				x + width, y, 0
		});
        vertexData.flip();
        vboVertexHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void draw() {
		//GL11.glLoadIdentity();
		//GL11.glTranslatef(this.x, this.y, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
		GL11.glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0L);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDrawArrays(GL11.GL_QUADS, 0, amountOfVertices);
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
	
}
