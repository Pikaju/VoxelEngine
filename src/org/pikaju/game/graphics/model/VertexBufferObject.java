package org.pikaju.game.graphics.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.pikaju.game.util.math.Vec3;

public class VertexBufferObject extends Model {

	private int vertexID = -1;
	private int colorID = -1;
	private int normalID = -1;
	private int indexID = -1;
	
	private int size;
	
	public VertexBufferObject() {
		init();
	}
	
	public void init() {
		cleanup();
		size = 0;
		vertexID = GL15.glGenBuffers();
		colorID = GL15.glGenBuffers();
		normalID = GL15.glGenBuffers();
		indexID = GL15.glGenBuffers();
	}
	
	public void cleanup() {
		if (vertexID != -1) {
			GL15.glDeleteBuffers(vertexID);
			vertexID = -1;
		}
		if (colorID != -1) {
			GL15.glDeleteBuffers(colorID);
			colorID = -1;
		}
		if (normalID != -1) {
			GL15.glDeleteBuffers(normalID);
			normalID = -1;
		}
		if (indexID != -1) {
			GL15.glDeleteBuffers(indexID);
			indexID = -1;
		}
	}
	
	public void setData(Vec3[] vertices, Vec3[] colors, Vec3[] normals, int[] indices) {
		size = indices.length;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, getArrayData(vertices), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, getArrayData(colors), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, getArrayData(normals), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, getArrayData(indices), GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		super.render();
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexID);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorID);
		GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalID);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexID);
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
	}
	
	private FloatBuffer getArrayData(Vec3[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length * 3);
		for (int i = 0; i < array.length; i++) array[i].addToBuffer(buffer);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer getArrayData(int[] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		for (int i = 0; i < array.length; i++) buffer.put(array[i]);
		buffer.flip();
		return buffer;
	}
}
