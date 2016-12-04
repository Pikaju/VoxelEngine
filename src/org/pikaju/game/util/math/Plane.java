package org.pikaju.game.util.math;

public class Plane {
	
	public float a;
	public float b;
	public float c;
	public float d;
	
	public Plane(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		normalize();
	}
	
	private void normalize() {
		float t = (float) Math.sqrt(a * a + b * b + c * c);
		a /= t;
		b /= t;
		c /= t;
		d /= t;
	}
}
