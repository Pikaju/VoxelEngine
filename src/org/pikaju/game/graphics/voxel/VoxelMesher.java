package org.pikaju.game.graphics.voxel;

import java.util.ArrayList;

import org.pikaju.game.graphics.model.VertexData;
import org.pikaju.game.util.Util;
import org.pikaju.game.util.math.Vec3;
import org.pikaju.game.world.Chunk;

public class VoxelMesher {

	private static final float ANTI_VOXEL_BLEEDING = 0.001f;
	
	public static VertexData meshChunk(Chunk chunk) {
		VertexData data = new VertexData();
		@SuppressWarnings("unused")
		int optimized = 0;
		for (int l = 0; l < Chunk.SIZE; l++) { //TOP
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = i + chunk.cx * Chunk.SIZE;
					int y = l + chunk.cy * Chunk.SIZE;
					int z = j + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x, y + 1, z)) {
						quads.add(new Quad(new Vec3(x, y + 1, z), new Vec3(x + 1, y + 1, z), new Vec3(x + 1, y + 1, z + 1), new Vec3(x, y + 1, z + 1), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		for (int l = 0; l < Chunk.SIZE; l++) { //BOTTOM
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = i + chunk.cx * Chunk.SIZE;
					int y = l + chunk.cy * Chunk.SIZE;
					int z = j + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x, y - 1, z)) {
						quads.add(new Quad(new Vec3(x, y, z), new Vec3(x, y, z + 1), new Vec3(x + 1, y, z + 1), new Vec3(x + 1, y, z), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		for (int l = 0; l < Chunk.SIZE; l++) { //LEFT
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = l + chunk.cx * Chunk.SIZE;
					int y = i + chunk.cy * Chunk.SIZE;
					int z = j + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x - 1, y, z)) {
						quads.add(new Quad(new Vec3(x, y, z), new Vec3(x, y + 1, z), new Vec3(x, y + 1, z + 1), new Vec3(x, y, z + 1), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		for (int l = 0; l < Chunk.SIZE; l++) { //RIGHT
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = l + chunk.cx * Chunk.SIZE;
					int y = i + chunk.cy * Chunk.SIZE;
					int z = j + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x + 1, y, z)) {
						quads.add(new Quad(new Vec3(x + 1, y, z), new Vec3(x + 1, y, z + 1), new Vec3(x + 1, y + 1, z + 1), new Vec3(x + 1, y + 1, z), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		for (int l = 0; l < Chunk.SIZE; l++) { //BACK
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = i + chunk.cx * Chunk.SIZE;
					int y = j + chunk.cy * Chunk.SIZE;
					int z = l + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x, y, z - 1)) {
						quads.add(new Quad(new Vec3(x, y, z), new Vec3(x + 1, y, z), new Vec3(x + 1, y + 1, z), new Vec3(x, y + 1, z), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		for (int l = 0; l < Chunk.SIZE; l++) { //FRONT
			ArrayList<Quad> quads = new ArrayList<Quad>();
			for (int i = 0; i < Chunk.SIZE; i++) {
				for (int j = 0; j < Chunk.SIZE; j++) {
					int x = i + chunk.cx * Chunk.SIZE;
					int y = j + chunk.cy * Chunk.SIZE;
					int z = l + chunk.cz * Chunk.SIZE;
					Vec3 color = Util.intToColor(chunk.world.getCube(x, y, z));
					if (color.equals(new Vec3(0, 0, 0))) continue;
					if (!chunk.world.isSolid(x, y, z + 1)) {
						quads.add(new Quad(new Vec3(x, y, z + 1), new Vec3(x, y + 1, z + 1), new Vec3(x + 1, y + 1, z + 1), new Vec3(x + 1, y, z + 1), color));
					}
				}
			}
			int quadsSize = quads.size();
			optimizeQuads(quads);
			optimized += quadsSize - quads.size();
			for (int i = 0; i < quads.size(); i++) {
				quads.get(i).increaseSize(ANTI_VOXEL_BLEEDING);
				data.quad(quads.get(i).p0, quads.get(i).p1, quads.get(i).p2, quads.get(i).p3, quads.get(i).color);
			}
		}
		//System.out.println("Optimized: " + optimized);
		return data;
	}

	public static void optimizeQuads(ArrayList<Quad> quads) {
		for (int i = 0; i < quads.size(); i++) {
			Vec3 p00 = quads.get(i).p0;
			Vec3 p01 = quads.get(i).p1;
			Vec3 p02 = quads.get(i).p2;
			Vec3 p03 = quads.get(i).p3;
			for (int j = 0; j < quads.size(); j++) {
				if (!quads.get(i).color.equals(quads.get(j).color)) continue;
				
				Vec3 p10 = quads.get(j).p0;
				Vec3 p11 = quads.get(j).p1;
				Vec3 p12 = quads.get(j).p2;
				Vec3 p13 = quads.get(j).p3;

				int matches = countMatches(new Vec3[] { p00, p01, p02, p03 }, new Vec3[] { p10, p11, p12, p13 });

				if (matches >= 3 || matches < 2) continue;
				quads.add(new Quad(quads.get(i), quads.get(j)));
				if (j > i) quads.remove(j);
				quads.remove(i);
				if (j < i) quads.remove(j);
				i = 0;
				break;
			}
		}
	}

	private static int countMatches(Vec3[] v0, Vec3[] v1) {
		int matches = 0;
		for (int i = 0; i < v0.length; i++) {
			for (int j = 0; j < v1.length; j++) {
				if (v0[i].equals(v1[j])) {
					matches++;
				}
			}
		}
		return matches;
	}
}
