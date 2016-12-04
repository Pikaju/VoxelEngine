package org.pikaju.game.world.effects;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.pikaju.game.graphics.Shader;
import org.pikaju.game.graphics.Texture;
import org.pikaju.game.util.Camera;

public class SSAO {

	private Texture renderTarget;
	private Shader shader;
	
	public SSAO() {
		renderTarget = new Texture(1, Display.getWidth(), Display.getHeight());
		renderTarget.initFrameBuffer(new int[] { GL30.GL_COLOR_ATTACHMENT0 });
		shader = new Shader("/ssao.vs", "/ssao.fs");
	}
	
	public void render(Camera camera, Texture worldRenderTarget) {
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		float aspect = (float) Display.getWidth() / (float) Display.getHeight();
		
		renderTarget.bindTarget();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		shader.enable();
		shader.setUniform("pixelPositions", 0);
		shader.setUniform("cameraPosition", camera.getPosition());
		worldRenderTarget.bind(1, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(1, 1, 1);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex3f(-aspect, -1, -1);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex3f(-aspect, 1, -1);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex3f(aspect, 1, -1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex3f(aspect, -1, -1);
		GL11.glEnd();
		shader.disable();
		renderTarget.unbindTarget();
		
		GL11.glPopMatrix();
	}

	public Texture getRenderTarget() {
		return renderTarget;
	}
}
