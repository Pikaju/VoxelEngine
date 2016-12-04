package org.pikaju.game;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.pikaju.game.graphics.FrustumCuller;
import org.pikaju.game.graphics.RenderingEngine;
import org.pikaju.game.graphics.Shader;
import org.pikaju.game.graphics.Texture;
import org.pikaju.game.threads.ThreadManager;
import org.pikaju.game.util.Camera;
import org.pikaju.game.world.World;

public class Launcher {

	public static Launcher i;
	
	private static final String NAME = "Anicavity";
	private static final float VERSION = 0.0f;
	private static final String TITLE = NAME + "  |  v" + VERSION;
	
	private boolean running;
	
	private World world;
	
	public Texture worldTarget;
	public Shader pp;
	
	public static void main(String[] args) {
		Thread.currentThread().setName(NAME);
		i = new Launcher();
		i.start();
	}
	
	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(1280, 720));
			//Display.setFullscreen(true);
			Display.setResizable(true);
			Display.setVSyncEnabled(true);
			Display.setTitle(TITLE);
			Display.create();
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glFrontFace(GL11.GL_CW);
		GL11.glLineWidth(10);
		
		Camera.initMatrix();
		RenderingEngine.init();
		FrustumCuller.init();
		ThreadManager.start();
		
		world = new World();
		
		worldTarget = new Texture(2, 1920, 1080);
		worldTarget.initFrameBuffer(new int[] { GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1 });
		pp = new Shader("/postprocessing.vs", "/postprocessing.fs");
	}
	
	public void cleanup() {
		world.cleanup();
		ThreadManager.stop();
		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
	}
	
	public void start() {
		if (running) {
			return;
		}
		running = true;
		init();
		run();
	}
	
	public void stop() {
		if (!running) {
			return;
		}
		running = false;
		cleanup();
		System.exit(0);
	}
	
	public void run() {
		long currentTime = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		
		float delta = 0.0f;
		float ns = 1000000000.0f / 60.0f;
		
		int frames = 0;
		
		while (running) {
			currentTime = System.nanoTime();
			timer += currentTime - lastTime;
			delta = (currentTime - lastTime) / ns;
			lastTime = currentTime;
			
			update(delta);
			render();
			
			frames++;
			Display.update();
			
			while (timer >= 1000000000) {
				timer -= 1000000000;
				System.out.println(frames + "fps");
				frames = 0;
			}
			
			if (Display.isCloseRequested()) stop();
			if (Display.wasResized()) Camera.initMatrix();
		}
	}
	
	public void update(float delta) {
		world.update(delta);
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_1) {
					RenderingEngine.togglePolygonMode();
				}
			}
		}
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		worldTarget.bindTarget();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		world.render(worldTarget);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glLoadIdentity();
		worldTarget.unbindTarget();
		
		float aspect = (float) Display.getWidth() / (float) Display.getHeight();
		
		pp.enable();
		pp.setUniform("pixelColors", 0);
		pp.setUniform("pixelPositions", 1);
		pp.setUniform("pixelOcclusion", 2);
		pp.setUniform("pixelAtmosphere", 3);
		pp.setUniform("fogDistance", world.getFogDistance());
		pp.setUniform("cameraPosition", world.getCamera().getPosition());
		worldTarget.bind(0, 0);
		worldTarget.bind(1, 1);
		world.getSSAO().getRenderTarget().bind(0, 2);
		world.getAtmosphere().getRenderTarget().bind(0, 3);
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
		pp.disable();
	}
}
