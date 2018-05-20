package common.vectorMath.objects3D;

import common.vectorMath.VectorUtils;

public class Plane {

	
	//Hessesche Normalenform
	private double a, b, c, d;

	public Plane(final Point p1, final Point p2, final Point p3) {
		final Point my = VectorUtils.subtract(p1, p2);
		final Point ny = VectorUtils.subtract(p1, p3);
		final Point normal = VectorUtils.crossP(my, ny);
		normal.normalize();
		this.a = normal.getX();
		this.b = normal.getY();
		this.c = normal.getZ();
		this.d = VectorUtils.scalar(p1, normal);
	}

	public Plane(final Point p1, final Point normal) {
		final Point normalNormalized = normal.clone();
		normalNormalized.normalize();
		this.a = normalNormalized.getX();
		this.b = normalNormalized.getY();
		this.c = normalNormalized.getZ();
		final double x = p1.getX();
		final double y = p1.getY();
		final double z = p1.getZ();
		this.d = this.a * x + this.b * y + this.c * z;
	}

	public Point getNormal() {
		return new Point(this.a, this.b, this.c);
	}

	public double getA() {
		return this.a;
	}

	public double getB() {
		return this.b;
	}

	public double getC() {
		return this.c;
	}

	public double getD() {
		return this.d;
	}
}
