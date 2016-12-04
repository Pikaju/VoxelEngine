package org.pikaju.game.graphics.model;

import org.lwjgl.opengl.GL11;
import org.pikaju.game.graphics.RenderingEngine;
import org.pikaju.game.util.math.Vec3;

public class Model {

	public Model() {
		init();
	}
	
	public void init() {
		
	}
	
	public void cleanup() {
		
	}
	
	public void setData(Vec3[] vertices, Vec3[] colors, Vec3[] normals, int[] indices) {
		
	}
	
	public void render() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, RenderingEngine.POLYGON_MODE.getGLConstant());
	}
}
