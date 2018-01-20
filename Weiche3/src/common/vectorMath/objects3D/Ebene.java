package common.vectorMath.objects3D;

import common.vectorMath.VectorUtils;

public class Ebene {

	private Point p1, p2, p3, normal;

	// Koordinatenform ax+bx+cx=d
	private double a, b, c, d;

	public Ebene(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		Point my = VectorUtils.subtract(p1, p2);
		Point ny = VectorUtils.subtract(p1, p3);
		this.normal = VectorUtils.crossP(my, ny);
		this.normal.normalize();

		this.a = normal.getX();
		this.b = normal.getY();
		this.c = normal.getZ();

		this.d = VectorUtils.scalar(this.p1, this.normal);
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public Point getP3() {
		return p3;
	}

	public Point getNormal() {
		return normal;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public double getD() {
		return d;
	}
}
