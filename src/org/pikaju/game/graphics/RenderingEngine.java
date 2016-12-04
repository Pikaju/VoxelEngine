package org.pikaju.game.graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.pikaju.game.graphics.model.Model;

public class RenderingEngine {
	public enum ModelType {
		VERTEX_BUFFER_OBJECT,
		DISPLAY_LIST
	}
	public static ModelType MODEL_TYPE = ModelType.VERTEX_BUFFER_OBJECT;
	
	public enum PolygonMode {
		LINE(GL11.GL_LINE), FILL(GL11.GL_FILL);
		
		private int glConstant;
		
		PolygonMode(int glConstant) {
			this.glConstant = glConstant;
		}
		
		public int getGLConstant() {
			return glConstant;
		}
	}
	
	public static PolygonMode POLYGON_MODE = PolygonMode.FILL;
	
	private static ArrayList<Model> models;
	
	public static void init() {
		models = new ArrayList<Model>();
	}
	
	public static void cleanup() {
		for (int i = 0; i < models.size(); i++) {
			models.get(i).cleanup();
		}
		models.clear();
	}
	
	public static void addModel(Model model) {
		models.add(model);
	}
	
	public static void removeModel(Model model) {
		models.remove(model);
	}
	
	public static void toggleModelType() {
		if (MODEL_TYPE == ModelType.DISPLAY_LIST) MODEL_TYPE = ModelType.VERTEX_BUFFER_OBJECT;
		else if (MODEL_TYPE == ModelType.VERTEX_BUFFER_OBJECT) MODEL_TYPE = ModelType.DISPLAY_LIST;
	}
	
	public static void togglePolygonMode() {
		if (POLYGON_MODE == PolygonMode.FILL) POLYGON_MODE = PolygonMode.LINE;
		else if (POLYGON_MODE == PolygonMode.LINE) POLYGON_MODE = PolygonMode.FILL;
	}
}
