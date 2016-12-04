package org.pikaju.game.graphics.voxel;

import java.util.ArrayList;

import org.pikaju.game.util.math.Vec3;

public class Quad {

	public Vec3 p0;
	public Vec3 p1;
	public Vec3 p2;
	public Vec3 p3;
	public Vec3 color;
	
	public Quad(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, Vec3 color) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.color = color;
	}

	public Quad(Quad q0, Quad q1) {
		ArrayList<Vec3> p = new ArrayList<Vec3>();
		p.add(q0.p0);
		p.add(q1.p0);
		p.add(q0.p1);
		p.add(q1.p1);
		p.add(q0.p2);
		p.add(q1.p2);
		p.add(q0.p3);
		p.add(q1.p3);
		for (int i = 0; i < p.size(); i++) {
			for (int j = i + 1; j < p.size(); j++) {
				if (p.get(i).equals(p.get(j))) {
					p.remove(j);
					p.remove(i);
					i--;
					break;
				}
			}
		}
		p0 = p.get(0);
		p1 = p.get(1);
		p2 = p.get(2);
		p3 = p.get(3);
		color = q0.color;
	}

	public void increaseSize(float size) {
		if (p0.getX() > p1.getX() || p0.getX() > p2.getX() || p0.getX() > p3.getX()) p0.add(new Vec3(size, 0, 0));
		if (p1.getX() > p0.getX() || p1.getX() > p2.getX() || p1.getX() > p3.getX()) p1.add(new Vec3(size, 0, 0));
		if (p2.getX() > p0.getX() || p2.getX() > p1.getX() || p2.getX() > p3.getX()) p2.add(new Vec3(size, 0, 0));
		
		if (p0.getY() > p1.getY() || p0.getY() > p2.getY() || p0.getY() > p3.getY()) p0.add(new Vec3(0, size, 0));
		if (p1.getY() > p0.getY() || p1.getY() > p2.getY() || p1.getY() > p3.getY()) p1.add(new Vec3(0, size, 0));
		if (p2.getY() > p0.getY() || p2.getY() > p1.getY() || p2.getY() > p3.getY()) p2.add(new Vec3(0, size, 0));
		
		if (p0.getZ() > p1.getZ() || p0.getZ() > p2.getZ() || p0.getZ() > p3.getZ()) p0.add(new Vec3(0, 0, size));
		if (p1.getZ() > p0.getZ() || p1.getZ() > p2.getZ() || p1.getZ() > p3.getZ()) p1.add(new Vec3(0, 0, size));
		if (p2.getZ() > p0.getZ() || p2.getZ() > p1.getZ() || p2.getZ() > p3.getZ()) p2.add(new Vec3(0, 0, size));
	}
}
