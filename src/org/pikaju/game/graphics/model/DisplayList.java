package org.pikaju.game.graphics.model;

import org.lwjgl.opengl.GL11;
import org.pikaju.game.util.math.Vec3;

public class DisplayList extends Model {

	private int id;

	public DisplayList() {
		init();
	}

	public void init() {
		id = GL11.glGenLists(1);
	}

	public void cleanup() {
		GL11.glDeleteLists(id, 1);
	}

	public void setData(Vec3[] vertices, Vec3[] colors, Vec3[] normals, int[] indices) {
		GL11.glNewList(id, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < indices.length; i += 3) {
			GL11.glNormal3f(normals[indices[i + 0]].getX(), normals[indices[i + 0]].getY(), normals[indices[i + 0]].getZ());
			GL11.glColor3f(colors[indices[i + 0]].getX(), colors[indices[i + 0]].getY(), colors[indices[i + 0]].getZ());
			GL11.glVertex3f(vertices[indices[i + 0]].getX(), vertices[indices[i + 0]].getY(), vertices[indices[i + 0]].getZ());
			GL11.glNormal3f(normals[indices[i + 1]].getX(), normals[indices[i + 1]].getY(), normals[indices[i + 1]].getZ());
			GL11.glColor3f(colors[indices[i + 1]].getX(), colors[indices[i + 1]].getY(), colors[indices[i + 1]].getZ());
			GL11.glVertex3f(vertices[indices[i + 1]].getX(), vertices[indices[i + 1]].getY(), vertices[indices[i + 1]].getZ());
			GL11.glNormal3f(normals[indices[i + 2]].getX(), normals[indices[i + 2]].getY(), normals[indices[i + 2]].getZ());
			GL11.glColor3f(colors[indices[i + 2]].getX(), colors[indices[i + 2]].getY(), colors[indices[i + 2]].getZ());
			GL11.glVertex3f(vertices[indices[i + 2]].getX(), vertices[indices[i + 2]].getY(), vertices[indices[i + 2]].getZ());
		}
		GL11.glEnd();
		GL11.glEndList();
	}
	
	public void render() {
		super.render();
		GL11.glCallList(id);
	}
}
