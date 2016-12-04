package org.pikaju.game.graphics;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Texture {

	private IntBuffer textures;
	private int width;
	private int height;
	private int frameBuffer = -1;
	private int renderBuffer = -1;
	
	public Texture(int numTextures, int width, int height) {
		textures = BufferUtils.createIntBuffer(numTextures);
		this.width = width;
		this.height = height;
		GL11.glGenTextures(textures);
		for (int i = 0; i < textures.capacity(); i++) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures.get(i));
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA32F, width, height, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (ByteBuffer) null);
		}
	}
	
	public void initFrameBuffer(int[] attachments) {
		IntBuffer drawBuffers = BufferUtils.createIntBuffer(attachments.length);
		boolean hasDepth = false;
		for (int i = 0; i < attachments.length; i++) {
			if (attachments[i] == GL30.GL_DEPTH_ATTACHMENT) {
				hasDepth = true;
				drawBuffers.put(GL11.GL_NONE);
			} else {
				drawBuffers.put(attachments[i]);
			}
			if(attachments[i] == GL11.GL_NONE) continue;
			if(frameBuffer == -1) {
				frameBuffer = GL30.glGenFramebuffers();
				GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
			}
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, attachments[i], GL11.GL_TEXTURE_2D, textures.get(i), 0);
		}
		
		if (frameBuffer == -1) return;
		
		if(!hasDepth) {
			renderBuffer = GL30.glGenRenderbuffers();
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBuffer);
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
			GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBuffer);
		}
		
		drawBuffers.flip();
		GL20.glDrawBuffers(drawBuffers);
		//GL11.glDrawBuffer(GL11.GL_NONE);
		//GL11.glReadBuffer(GL11.GL_NONE);
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}
	
	public void cleanup() {
		if (textures != null) textures = null;
		if (frameBuffer != -1) {
			GL30.glDeleteFramebuffers(frameBuffer);
			frameBuffer = -1;
		}
		if (renderBuffer != -1) {
			GL30.glDeleteRenderbuffers(renderBuffer);;
			renderBuffer = -1;
		}
	}
	
	public void bind(int id, int slot) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures.get(id));
	}
	
	public void bindTarget() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
		GL11.glViewport(0, 0, width, height);
	}
	
	public void unbindTarget() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
}
