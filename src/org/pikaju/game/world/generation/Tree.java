package org.pikaju.game.world.generation;

import org.pikaju.game.world.World;

public class Tree {

	public static void generateTree(World world, int x, int y, int z) {
		float w = (int) (Math.random() * 4) + 8;
		int h = (int) (20 + (Math.random() * 10));
		for (int i = 0; i < h; i++) {
			int ww = (int) (w * Math.pow(0.97, i));
			Structure.sphere(world, x - ww / 2, y - 6 + i, z - ww / 2, ww, ww, ww, 0xff496e41);
		}
		Structure.sphere(world, (int) (x - 8), y + h - 4, (int) (z - 8), 16, 8, 16, 0x009924);
		for (int i = 0; i < 3; i++) {
			Structure.sphere(world, x - 8 + (int) (Math.random() * 16 - 8), y + h - 4 + (int) (Math.random() * 16 - 8), z - 8 + (int) (Math.random() * 16 - 8), 16, 8, 16, 0x009924);
		}
	}
}
