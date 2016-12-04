package org.pikaju.game.world.generation;

import org.pikaju.game.util.Util;
import org.pikaju.game.util.math.Vec3;
import org.pikaju.game.world.Chunk;

public class Biome {

	public static void generate(Chunk chunk) {
		for (int x = 0; x < Chunk.SIZE; x++) {
			for (int y = 0; y < Chunk.SIZE; y++) {
				for (int z = 0; z < Chunk.SIZE; z++) {
					float scale = 0.02f;
					int xx = x + chunk.cx * Chunk.SIZE;
					int yy = y + chunk.cy * Chunk.SIZE;
					int zz = z + chunk.cz * Chunk.SIZE;
					if ((chunk.world.noise.noise(xx * scale, yy * scale, zz * scale) + 1.0) * 0.5 > yy / 128.0f) {
						chunk.setCube(x, y, z, Util.colorToInt(new Vec3(0.0f, 0.8f, 0.0f)));
						if ((chunk.world.noise.noise(xx * scale, (yy + 8) * scale, zz * scale) + 1.0) * 0.5 > yy / 128.0f) {
							chunk.setCube(x, y, z, Util.colorToInt(new Vec3(0.4f, 0.4f, 0.4f)));
						}
					}
				}
			}
		}
	}
}
