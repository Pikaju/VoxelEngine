package org.pikaju.game.util;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.pikaju.game.util.math.Vec3;

public class Camera {

	private float x;
	private float y;
	private float z;
	
	private float rx;
	private float ry;
	private float rz;
	
	public Camera(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void update(float delta) {
		if (Mouse.isButtonDown(0) && !Mouse.isGrabbed()) {
			Mouse.setGrabbed(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}
		
		float sensitivity = 0.125f;
		if (Mouse.isGrabbed()) {
			ry += Mouse.getDX() * sensitivity;
			rx -= Mouse.getDY() * sensitivity;
			if (rx > 90.0f) rx = 90.0f;
			if (rx < -90.0f) rx = -90.0f;
		}
		float dx = 0.0f;
		float dy = 0.0f;
		float dz = 0.0f;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) dz++;
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) dx--;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) dz--;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) dx++;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) dy++;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) dy--;
		float speed = 0.2f * delta;
		x += (Math.cos(Math.toRadians(ry)) * dx + Math.sin(Math.toRadians(ry)) * dz) * speed;
		y += dy * speed;
		z += (-Math.cos(Math.toRadians(ry)) * dz + Math.sin(Math.toRadians(ry)) * dx) * speed;
	}
	
	public void applyMatrix() {
		applyRotation();
		GL11.glTranslatef(-x, -y, -z);
	}

	public void applyRotation() {
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
	}
	
	public static void initMatrix() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(90.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.1f, 1000.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRX() {
		return rx;
	}

	public void setRX(float rx) {
		this.rx = rx;
	}

	public float getRY() {
		return ry;
	}

	public void setRY(float ry) {
		this.ry = ry;
	}

	public float getRZ() {
		return rz;
	}

	public void setRZ(float rz) {
		this.rz = rz;
	}

	public Vec3 getPosition() {
		return new Vec3(x, y, z);
	}
}
