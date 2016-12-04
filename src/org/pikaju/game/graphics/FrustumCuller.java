package org.pikaju.game.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.pikaju.game.util.math.Plane;

public class FrustumCuller {

	private static Plane[] planes;
	
	public static void init() {
		planes = new Plane[6];
	}
	
	public static void extractPlanes() {
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		FloatBuffer mvp = BufferUtils.createFloatBuffer(16);
		
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		
		GL11.glPushMatrix();
		GL11.glLoadMatrix(projection);
		GL11.glMultMatrix(modelview);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mvp);
		GL11.glPopMatrix();
		
		planes[0] = new Plane(mvp.get(3) + mvp.get(0), mvp.get(7) + mvp.get(4), mvp.get(11) + mvp.get(8), mvp.get(15) + mvp.get(12));
		planes[1] = new Plane(mvp.get(3) - mvp.get(0), mvp.get(7) - mvp.get(4), mvp.get(11) - mvp.get(8), mvp.get(15) - mvp.get(12));
		planes[2] = new Plane(mvp.get(3) + mvp.get(1), mvp.get(7) + mvp.get(5), mvp.get(11) + mvp.get(9), mvp.get(15) + mvp.get(13));
		planes[3] = new Plane(mvp.get(3) - mvp.get(1), mvp.get(7) - mvp.get(5), mvp.get(11) - mvp.get(9), mvp.get(15) - mvp.get(13));
		planes[4] = new Plane(mvp.get(3) + mvp.get(2), mvp.get(7) + mvp.get(6), mvp.get(11) + mvp.get(10), mvp.get(15) + mvp.get(14));
		planes[5] = new Plane(mvp.get(3) - mvp.get(2), mvp.get(7) - mvp.get(6), mvp.get(11) - mvp.get(10), mvp.get(15) - mvp.get(14));
	}
	
	public static boolean isSphereInFrustum(float x, float y, float z, float radius) {
		for (int i = 0; i < planes.length; i++) {
			if (planes[i].a * x + planes[i].b * y + planes[i].c * z + planes[i].d <= -radius) {
				return false;
			}
		}
		return true;
	}
}
