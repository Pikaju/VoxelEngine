package org.pikaju.game.world;

import org.pikaju.game.graphics.FrustumCuller;
import org.pikaju.game.graphics.Shader;
import org.pikaju.game.graphics.Texture;
import org.pikaju.game.util.Camera;
import org.pikaju.game.util.Noise;
import org.pikaju.game.util.math.Vec3;
import org.pikaju.game.world.effects.Atmosphere;
import org.pikaju.game.world.effects.SSAO;

public class World {

	private static final int vision = 4;

	private ChunkManager chunkManager;
	
	private Camera camera;
	private Shader shader;
	public Noise noise;
	
	private Atmosphere atmosphere;
	private SSAO ssao;

	private float fogDistance = 0.0f;
	private float maxFogDistance = 0.0f;

	public World() {
		noise = new Noise();
		camera = new Camera(0, 64, 0);
		shader = new Shader("/lighting.vs", "/lighting.fs");
		chunkManager = new ChunkManager(this);
		chunkManager.start();
		atmosphere = new Atmosphere();
		ssao = new SSAO();
	}

	public void cleanup() {
		chunkManager.cleanup();
	}
	
	public void update(float delta) {
		camera.update(delta);
		fogDistance += (maxFogDistance - fogDistance) * 0.01f * delta;
		chunkManager.update(delta);
	}

	public void render(Texture worldTarget) {
		shader.enable();
		shader.setUniform("cameraPosition", new Vec3(camera.getX(), camera.getY(), camera.getZ()));
		camera.applyMatrix();
		FrustumCuller.extractPlanes();
		chunkManager.render();
		atmosphere.render(camera);
		ssao.render(camera, worldTarget);
	}

	public Camera getCamera() {
		return camera;
	}
	
	public float getFogDistance() {
		return fogDistance;
	}

	public void setMaxFogDistance(float maxFogDistance) {
		this.maxFogDistance = maxFogDistance;
	}

	public int getVision() {
		return vision;
	}

	public int getCube(int x, int y, int z) {
		return chunkManager.getCube(x, y, z);
	}

	public void setCube(int x, int y, int z, int color) {
		chunkManager.setCube(x, y, z, color);
	}

	public int getHeight(int x, int z, int start) {
		return chunkManager.getHeight(x, z, start);
	}

	public boolean isChunkGenerated(int x, int y, int z) {
		return chunkManager.isChunkGenerated(x, y, z);
	}

	public boolean isSolid(int x, int y, int z) {
		return chunkManager.isSolid(x, y, z);
	}

	public Atmosphere getAtmosphere() {
		return atmosphere;
	}

	public SSAO getSSAO() {
		return ssao;
	}
}
