package org.pikaju.game.util;

import java.util.Random;

public class Noise {

	public Noise() {
		this(System.currentTimeMillis());
	}

	public Noise(long seed) {
		seed(seed);
	}

	public double noise(double x, double y, double z) {
		int X = (int) Math.floor(x) & 255, Y = (int) Math.floor(y) & 255, Z = (int) Math.floor(z) & 255;
		x -= Math.floor(x);
		y -= Math.floor(y);
		z -= Math.floor(z);
		double u = fade(x), v = fade(y), w = fade(z);
		int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z,

		B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z;

		return lerp(
				w,
				lerp(v,
						lerp(u, grad(p[AA], x, y, z), grad(p[BA], x - 1, y, z)),
						lerp(u, grad(p[AB], x, y - 1, z),
								grad(p[BB], x - 1, y - 1, z))),
				lerp(v,
						lerp(u, grad(p[AA + 1], x, y, z - 1),
								grad(p[BA + 1], x - 1, y, z - 1)),
						lerp(u, grad(p[AB + 1], x, y - 1, z - 1),
								grad(p[BB + 1], x - 1, y - 1, z - 1))));
	}
	
	public double octaves(double x, double y, double z, int octaves, double frequency, double scale) {
		double result = 0.0;
		for (int i = 1; i < octaves + 1; i++) {
			result += noise(x * i * frequency, y * i * frequency, z * i * frequency) * (i * scale);
		}
		return result;
	}

	static double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	static double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}

	static double grad(int hash, double x, double y, double z) {
		int h = hash & 15;
		double u = h < 8 ? x : y, v = h < 4 ? y : h == 12 || h == 14 ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	public void seed(long seed) {
		Random random = new Random(seed);
		int[] permutation = new int[256];
		for (int i = 0; i < permutation.length; i++) {
			permutation[i] = random.nextInt(256);
		}
		for (int i = 0; i < 256; i++)
			p[256 + i] = p[i] = permutation[i];
	}

	private int p[] = new int[512];
}
