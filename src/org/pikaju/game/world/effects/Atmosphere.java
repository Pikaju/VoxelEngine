package org.pikaju.game.world.effects;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.pikaju.game.graphics.Shader;
import org.pikaju.game.graphics.Texture;
import org.pikaju.game.util.Camera;

public class Atmosphere {

	private Texture renderTarget;
	private Shader shader;

	public Atmosphere() {
		renderTarget = new Texture(1, Display.getWidth(), Display.getHeight());
		renderTarget.initFrameBuffer(new int[] { GL30.GL_COLOR_ATTACHMENT0 });
		shader = new Shader("/atmosphere.vs", "/atmosphere.fs");
	}

	public void render(Camera camera) {
		renderTarget.bindTarget();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.enable();
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		camera.applyRotation();
		GL11.glScalef(512.0f, 512.0f, 512.0f);
		renderCube();
		GL11.glPopMatrix();
		shader.disable();
		renderTarget.unbindTarget();
	}
	
	public Texture getRenderTarget() {
		return renderTarget;
	}

	public void renderCube() {
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);

		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);

		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);

		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);

		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);

		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		
		GL11.glEnd();
	}
}
