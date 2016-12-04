package org.pikaju.game.graphics.model;

import java.util.ArrayList;

import org.pikaju.game.graphics.RenderingEngine;
import org.pikaju.game.graphics.RenderingEngine.ModelType;
import org.pikaju.game.util.Util;
import org.pikaju.game.util.math.Vec3;

public class VertexData {

	private ArrayList<Vec3> vertices;
	private ArrayList<Vec3> colors;
	private ArrayList<Vec3> normals;
	private ArrayList<Integer> indices;
	
	public VertexData() {
		vertices = new ArrayList<Vec3>();
		colors = new ArrayList<Vec3>();
		normals = new ArrayList<Vec3>();
		indices = new ArrayList<Integer>();
	}
	
	public void quad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, Vec3 color) {
		indices.add(vertices.size() + 0);
		indices.add(vertices.size() + 1);
		indices.add(vertices.size() + 2);
		indices.add(vertices.size() + 0);
		indices.add(vertices.size() + 2);
		indices.add(vertices.size() + 3);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		colors.add(color);
		Vec3 normal = v3.copy().sub(v1).cross(v3.copy().sub(v2)).normalize();
		normals.add(normal);
		normals.add(normal);
		normals.add(normal);
		normals.add(normal);
	}

	public Model createModel(ModelType type) {
		Model model = null;
		if (type == RenderingEngine.ModelType.DISPLAY_LIST) model = new DisplayList();
		if (type == RenderingEngine.ModelType.VERTEX_BUFFER_OBJECT) model = new VertexBufferObject();
		model.setData(vertices.toArray(new Vec3[0]), colors.toArray(new Vec3[0]), normals.toArray(new Vec3[0]), Util.listToArray(indices));
		if (indices.size() == 0) model.cleanup();
		return model;
	}
}
