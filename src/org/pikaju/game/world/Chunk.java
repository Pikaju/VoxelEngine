package org.pikaju.game.world;

import org.pikaju.game.graphics.FrustumCuller;
import org.pikaju.game.graphics.RenderingEngine;
import org.pikaju.game.graphics.model.Model;
import org.pikaju.game.graphics.model.VertexData;
import org.pikaju.game.graphics.voxel.VoxelMesher;
import org.pikaju.game.util.Util;
import org.pikaju.game.util.math.Vec3;
import org.pikaju.game.world.generation.Biome;
import org.pikaju.game.world.generation.Tree;

public class Chunk implements Runnable {

	public static final int SIZE = 64;
	
	private int[][][] cubes;
	private boolean terrainGenerated = false;
	private boolean running = false;
	private Model model;
	private VertexData vertexData;
	
	public World world;
	public int cx;
	public int cy;
	public int cz;
	
	public Chunk(World world, int cx, int cy, int cz) {
		this.world = world;
		this.cx = cx;
		this.cy = cy;
		this.cz = cz;
		cubes = new int[SIZE][SIZE][SIZE];
	}
	
	public void cleanup() {
		running = false;
		if (model != null) {
			RenderingEngine.removeModel(model);
			model.cleanup();
			model = null;
			vertexData = null;
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		if (model != null) {
			if (FrustumCuller.isSphereInFrustum(cx * Chunk.SIZE + Chunk.SIZE / 2, cy * Chunk.SIZE + Chunk.SIZE / 2, cz * Chunk.SIZE + Chunk.SIZE / 2, (float) Math.sqrt(SIZE * SIZE * SIZE))) model.render();
		} else if(vertexData != null) {
			createModel(vertexData);
			vertexData = null;
		}
	}
	
	public void run() {
		running = true;
		if (!terrainGenerated) {
			if (cy >= 0 && cy <= 128 / Chunk.SIZE) Biome.generate(this);
			terrainGenerated = true;
			running = false;
		} else {
			int height = world.getHeight(cx * SIZE + SIZE / 2, cz * SIZE + SIZE / 2, 128);
			if (height > cy * SIZE && height < cy * SIZE + SIZE && world.getCube(cx * SIZE + SIZE / 2, height, cz * SIZE + SIZE / 2) == Util.colorToInt(new Vec3(0.0f, 0.8f, 0.0f))) {
				Tree.generateTree(world, cx * SIZE + SIZE / 2, height, cz * SIZE + SIZE / 2);
			}
			createModelData();
			running = false;
		}
	}
	
	public void createModel(VertexData vertexData) {
		if (model != null) {
			model.cleanup();
		}
		model = vertexData.createModel(RenderingEngine.MODEL_TYPE);
		RenderingEngine.addModel(model);
	}
	
	public VertexData createModelData() {
		VertexData vertexData = VoxelMesher.meshChunk(this);
		this.vertexData = vertexData;
		return vertexData;
	}

	public int getCube(int x, int y, int z) {
		return cubes[x][y][z];
	}

	public void setCube(int x, int y, int z, int color) {
		cubes[x][y][z] = color;
	}
	
	public boolean isFilled() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				for (int z = 0; z < SIZE; z++) {
					if (cubes[x][y][z] != 0x00000000) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Vec3 getPosition() {
		return new Vec3(cx, cy, cz);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Chunk)) {
			return false;
		}
		Chunk v = (Chunk) obj;
		return v.getPosition().equals(getPosition());
	}

	public boolean isModelCreated() {
		return model != null;
	}
	
	public boolean isVertexDataCreated() {
		return vertexData != null;
	}
	
	public boolean isTerrainGenerated() {
		return terrainGenerated;
	}
	
	public boolean isRunning() {
		return running;
	}
}
