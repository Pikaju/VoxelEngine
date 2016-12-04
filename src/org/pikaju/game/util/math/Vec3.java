package org.pikaju.game.util.math;

import java.nio.FloatBuffer;

public class Vec3 {

	private float x;
	private float y;
	private float z;
	
	public Vec3(float x, float y, float z) {
		set(x, y, z);
	}
	
	public Vec3(float v) {
		this(v, v, v);
	}
	
	public Vec3() {
		this(0.0f);
	}
	
	public Vec3(double x, double y, double z) {
		this((float) x, (float) y, (float) z);
	}

	public Vec3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vec3 cross(Vec3 v) {
		float xx = y * v.z - z * v.y;
		float yy = z * v.x - x * v.z;
		float zz = x * v.y - y * v.x;
		return set(xx, yy, zz);
	}
	
	public Vec3 add(Vec3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	
	public Vec3 add(float v) {
		x += v;
		y += v;
		z += v;
		return this;
	}
	
	public Vec3 sub(Vec3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	
	public Vec3 mul(Vec3 v) {
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return this;
	}
	
	public Vec3 mul(float v) {
		x *= v;
		y *= v;
		z *= v;
		return this;
	}
	
	public Vec3 div(Vec3 v) {
		x /= v.x;
		y /= v.y;
		z /= v.z;
		return this;
	}
	
	public Vec3 div(float v) {
		x /= v;
		y /= v;
		z /= v;
		return this;
	}
	
	public Vec3 normalize() {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		if (length != 0) {
			x /= length;
			y /= length;
			z /= length;
		}
		return this;
	}
	
	public float length() {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		return length;
	}
	
	public Vec3 copy() {
		return new Vec3(x, y, z);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void addToBuffer(FloatBuffer buffer) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
	}
	
	public int hashCode() {
		return (int) (x * 124.1f) ^ (int) (y * 1.421f) ^ (int) (z * 5101.9f);
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Vec3)) return false;
		Vec3 v = (Vec3) obj;
		return v.x == x && v.y == y && v.z == z;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public float sum() {
		return x + y + z;
	}
}
