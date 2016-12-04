package org.pikaju.game.graphics.voxel;

import org.pikaju.game.graphics.model.VertexData;
import org.pikaju.game.util.math.Vec3;
import org.pikaju.game.world.World;

public class Voxel {
	
	public static void createModel(VertexData vertexData, World world, int x, int y, int z, Vec3 color) {
		Vec3 v1 = new Vec3(x, y, z);
		Vec3 v2 = new Vec3(x + 1, y, z);
		Vec3 v3 = new Vec3(x + 1, y, z + 1);
		Vec3 v4 = new Vec3(x, y, z + 1);
		Vec3 v5 = new Vec3(x, y + 1, z);
		Vec3 v6 = new Vec3(x + 1, y + 1, z);
		Vec3 v7 = new Vec3(x + 1, y + 1, z + 1);
		Vec3 v8 = new Vec3(x, y + 1, z + 1);
		
		float scale = 0.08f;
		color.add(new Vec3((float) world.noise.noise(x * scale, y * scale * 4.0f, z * scale) * 0.1f));
		
		if (!world.isSolid(x, y + 1, z)) vertexData.quad(v5, v6, v7, v8, color);
		if (!world.isSolid(x, y - 1, z)) vertexData.quad(v2, v1, v4, v3, color);
		if (!world.isSolid(x - 1, y, z)) vertexData.quad(v8, v4, v1, v5, color);
		if (!world.isSolid(x + 1, y, z)) vertexData.quad(v6, v2, v3, v7, color);
		if (!world.isSolid(x, y, z - 1)) vertexData.quad(v5, v1, v2, v6, color);
		if (!world.isSolid(x, y, z + 1)) vertexData.quad(v7, v3, v4, v8, color);
	}
}
