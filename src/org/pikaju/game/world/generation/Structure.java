package org.pikaju.game.world.generation;

import org.pikaju.game.world.World;

public class Structure {

	public static void fill(World world, int x, int y, int z, int width, int height, int length, int color) {
		for (int xx = x; xx < x + width; xx++) {
			for (int yy = y; yy < y + height; yy++) {
				for (int zz = z; zz < z + length; zz++) {
					world.setCube(xx, yy, zz, color);
				}
			}
		}
	}
	
	public static void sphere(World world, int x, int y, int z, int width, int height, int length, int color) {
		for (int xx = x; xx < x + width; xx++) {
			for (int yy = y; yy < y + height; yy++) {
				for (int zz = z; zz < z + length; zz++) {
					float xdist = (xx - (x + width / 2.0f)) / (width * 0.5f);
					float ydist = (yy - (y + height / 2.0f)) / (height * 0.5f);
					float zdist = (zz - (z + length / 2.0f)) / (length * 0.5f);
					
					if (Math.sqrt(xdist * xdist + ydist * ydist + zdist * zdist) < 1) {
						world.setCube(xx, yy, zz, color);
					}
				}
			}
		}
	}
}
