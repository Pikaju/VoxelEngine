package org.pikaju.game.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.pikaju.game.threads.ThreadManager;
import org.pikaju.game.util.math.Vec3;

public class ChunkManager implements Runnable {

	private World world;
	private boolean running;

	private HashMap<Vec3, Chunk> chunks;

	public ChunkManager(World world) {
		this.world = world;
		chunks = new HashMap<Vec3, Chunk>();
	}
	
	public void start() {
		if (running) {
			return;
		}
		running = true;
		ThreadManager.requestThread(this, "Chunk Manager");
	}
	
	public void stop() {
		if (!running) {
			return;
		}
		running = false;
	}
	
	public void cleanup() {
		ArrayList<Chunk> chunkValues = new ArrayList<Chunk>();
		synchronized (chunks) {
			chunkValues.addAll(chunks.values());
		}
		for (int i = 0; i < chunkValues.size(); i++) {
			chunkValues.get(i).cleanup();
		}
		chunks.clear();
	}
	
	public void update(float delta) {
		
	}
	
	public void render() {
		ArrayList<Chunk> chunkValues = new ArrayList<Chunk>();
		long l = System.currentTimeMillis();
		synchronized (chunks) {
			chunkValues.addAll(chunks.values());
		}
		if (System.currentTimeMillis() - l > 10) System.out.println(System.currentTimeMillis() - l);
		for (int x = (int) Math.floor(world.getCamera().getX() / Chunk.SIZE) - (world.getVision() + 1); x < (int) Math.floor(world.getCamera().getX() / Chunk.SIZE) + (world.getVision() + 1); x++) {
			for (int y = (int) Math.floor(world.getCamera().getY() / Chunk.SIZE) - (world.getVision() + 1); y < (int) Math.floor(world.getCamera().getY() / Chunk.SIZE) + (world.getVision() + 1); y++) {
				for (int z = (int) Math.floor(world.getCamera().getZ() / Chunk.SIZE) - (world.getVision() + 1); z < (int) Math.floor(world.getCamera().getZ() / Chunk.SIZE) + (world.getVision() + 1); z++) {
					Chunk chunk = getChunk(x, y, z);
					if (chunk != null) {
						chunk.render();
						chunkValues.remove(chunk);
					}
				}
			}
		}
		for (int i = 0; i < chunkValues.size(); i++) {
			chunks.remove(chunkValues.get(i).getPosition());
			chunkValues.get(i).cleanup();
		}
	}
	
	public void addChunk(Chunk chunk, int x, int y, int z) {
		synchronized (chunks) {
			chunks.put(new Vec3(x, y, z), chunk);
		}
	}

	public Chunk getChunk(int x, int y, int z) {
		if (!chunks.containsKey(new Vec3(x, y, z)))
			return null;
		return chunks.get(new Vec3(x, y, z));
	}

	public int getCube(int x, int y, int z) {
		int cx = Math.floorDiv(x, Chunk.SIZE);
		int cy = Math.floorDiv(y, Chunk.SIZE);
		int cz = Math.floorDiv(z, Chunk.SIZE);

		Chunk chunk = getChunk(cx, cy, cz);
		if (chunk == null)
			return 0x00000000;

		int bx = Math.floorMod(x, Chunk.SIZE);
		int by = Math.floorMod(y, Chunk.SIZE);
		int bz = Math.floorMod(z, Chunk.SIZE);

		return chunk.getCube(bx, by, bz);
	}

	public void setCube(int x, int y, int z, int color) {
		int cx = Math.floorDiv(x, Chunk.SIZE);
		int cy = Math.floorDiv(y, Chunk.SIZE);
		int cz = Math.floorDiv(z, Chunk.SIZE);

		Chunk chunk = getChunk(cx, cy, cz);
		if (chunk == null)
			return;

		int bx = Math.floorMod(x, Chunk.SIZE);
		int by = Math.floorMod(y, Chunk.SIZE);
		int bz = Math.floorMod(z, Chunk.SIZE);

		chunk.setCube(bx, by, bz, color);
	}

	public boolean isSolid(int x, int y, int z) {
		return getCube(x, y, z) != 0x00000000;
	}
	
	public int getHeight(int x, int z, int start) {
		for (int i = start; i >= 0; i--) {
			if (isSolid(x, i, z))
				return i;
		}
		return -1;
	}

	public boolean isChunkGenerated(int x, int y, int z) {
		Chunk chunk = getChunk(x, y, z);
		if (chunk == null)
			return false;
		return chunk.isTerrainGenerated();
	}
	
	public void run() {
		while (running) {
			ArrayList<Vec3> chunksToGenerate = new ArrayList<Vec3>();
			float newFogDistance = 4096.0f;
			final Vec3 camPos = world.getCamera().getPosition().div(new Vec3(Chunk.SIZE));
			for (int x = (int) Math.floor(world.getCamera().getX() / Chunk.SIZE) - (world.getVision() + 1); x < (int) Math.floor(world.getCamera().getX() / Chunk.SIZE) + (world.getVision() + 1); x++) {
				for (int y = (int) Math.floor(world.getCamera().getY() / Chunk.SIZE) - (world.getVision() + 1); y < (int) Math.floor(world.getCamera().getY() / Chunk.SIZE) + (world.getVision() + 1); y++) {
					for (int z = (int) Math.floor(world.getCamera().getZ() / Chunk.SIZE) - (world.getVision() + 1); z < (int) Math.floor(world.getCamera().getZ() / Chunk.SIZE) + (world.getVision() + 1); z++) {
						Chunk chunk = getChunk(x, y, z);
						Vec3 chunkPos = new Vec3(x, y, z);
						if (chunk == null || (chunk != null && !chunk.isVertexDataCreated() && !chunk.isRunning())) {
							chunksToGenerate.add(chunkPos);
						}
						if (chunk == null || (chunk != null && !chunk.isModelCreated())) {
							if (newFogDistance > (camPos.copy().sub(chunkPos.copy().add(0.5f)).length() - 0.9f) * Chunk.SIZE) {
								newFogDistance = (camPos.copy().sub(chunkPos.copy().add(0.5f)).length() - 0.9f) * Chunk.SIZE;
							}
						}
					}
				}
			}
			world.setMaxFogDistance(newFogDistance);
			Collections.sort(chunksToGenerate, new Comparator<Vec3>() {
				public int compare(Vec3 o1, Vec3 o2) {
					return (int) ((int) camPos.copy().sub(o1).length() - (int) camPos.copy().sub(o2).length());
				}
			});
			for (int i = 0; i < chunksToGenerate.size(); i++) {
				Vec3 newChunk = chunksToGenerate.get(i);
				int x = (int) newChunk.getX();
				int y = (int) newChunk.getY();
				int z = (int) newChunk.getZ();
				Chunk chunk = getChunk(x, y, z);
				if (chunk == null) {
					chunk = new Chunk(world, x, y, z);
					addChunk(chunk, x, y, z);
					ThreadManager.requestThread(chunk, "Chunk Terrain: " + x + ", " + y + ", " + z);
					break;
				} else if(isChunkGenerated(x + 1, y, z) && isChunkGenerated(x - 1, y, z) && isChunkGenerated(x, y + 1, z) && isChunkGenerated(x, y - 1, z) && isChunkGenerated(x, y, z + 1) && isChunkGenerated(x, y, z - 1)) {
					ThreadManager.requestThread(chunk, "Chunk Model: " + x + ", " + y + ", " + z);
					break;
				}
			}
			try {
				Thread.sleep(32);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
