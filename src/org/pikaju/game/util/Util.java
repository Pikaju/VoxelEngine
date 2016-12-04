package org.pikaju.game.util;

import java.util.ArrayList;

import org.pikaju.game.util.math.Vec3;

public class Util {

	public static int[] listToArray(ArrayList<Integer> list) {
		int[] array = new int[list.size()];
		for (int i = 0; i < array.length; i++) array[i] = list.get(i);
		return array;
	}
	
	public static Vec3 intToColor(int c) {
		return new Vec3(((c >> 16) & 0xff) / 255.0f, ((c >> 8) & 0xff) / 255.0f, ((c) & 0xff) / 255.0f);
	}

	public static int colorToInt(Vec3 v) {
		return ((int) (v.getX() * 0xff) << 16) | ((int) (v.getY() * 0xff) << 8) | ((int) (v.getZ() * 0xff));
	}
}
