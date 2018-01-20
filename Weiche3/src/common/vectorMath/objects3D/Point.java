package vectorMath.objects3D;

import vectorMath.Dist;
import vectorMath.MathUtils;

public class Point {

	private double x = 0., y = 0., z = 0.; // x y and z coordinates

	public Point(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public Point(final double[] xyz) {
		x = xyz[0];
		y = xyz[1];
		z = xyz[2];
	}

	public Point() {
		// Point with coordinates [0,0,0]
	}

	public void move(double dx, double dy, double dz) {
		x += dx;
		y += dy;
		z += dz;
	}

	public void move(double[] dxyz) {
		x += dxyz[0];
		y += dxyz[1];
		z += dxyz[2];
	}

	public void add(Point p) {
		this.x += p.getX();
		this.y += p.getY();
		this.z += p.getZ();
	}

	public void subtract(Point p) {
		this.x -= p.getX();
		this.y -= p.getY();
		this.z -= p.getZ();
	}

	public void move(Point dp) {
		this.move(dp.getxyz());
	}

	public void move(final double dist, final double angle) {
		this.move(dist * Math.cos(angle), dist * Math.sin(angle), 0);
	}

	public void multiply(double fac) {
		this.x *= fac;
		this.y *= fac;
		this.z *= fac;
	}

	public void scale(Point factors) {
		this.scale(factors.getxyz());
	}

	public void scale(double[] factors) {
		this.x *= factors[0];
		this.y *= factors[1];
		this.z *= factors[2];
	}

	public void rotate(double dPhi) { // rotate a point around the center of [xy] axis
		rotate(new Point(0, 0, 0), new double[] { 0, 0, dPhi });
	}

	public void rotate(final Point center, final double dRot) {
		this.rotate(center, new double[] { 0, 0, dRot });
	}

	public void rotate(Point center, double[] dRot) { // rotate a point around center Point
		double dx = this.x - center.getX();
		double dy = this.y - center.getY();
		double dz = this.z - center.getZ();

		// rotate around X axis
		double rYZ = MathUtils.pythargoras(dz, dy); // YZ Plane
		double rotX = Math.atan2(dz, dy); // X Axis
		rotX += dRot[0];
		dy = rYZ * Math.cos(rotX);
		dz = rYZ * Math.sin(rotX);

		// rotate around Y axis
		double rXZ = MathUtils.pythargoras(dx, dz); // XZ Plane
		double rotY = Math.atan2(dx, dz); // Y Axis
		rotY += dRot[1];
		dx = rXZ * Math.sin(rotY);
		dz = rXZ * Math.cos(rotY);

		// rotate around Z axis
		double rXY = MathUtils.pythargoras(dy, dx); // XY Plane
		double rotZ = Math.atan2(dy, dx); // Z Axis
		rotZ += dRot[2];
		dx = rXY * Math.cos(rotZ);
		dy = rXY * Math.sin(rotZ);

		this.x = dx + center.getX();
		this.y = dy + center.getY();
		this.z = dz + center.getZ();
	}

	public void mirror(Axis axis) {
		switch (axis) {
		case X:
			this.y *= -1.;
			this.z *= -1.;
			break;
		case Y:
			this.x *= -1.;
			this.z *= -1.;
			break;
		case Z:
			this.x *= -1.;
			this.y *= -1.;
			break;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double[] getAngle() {
		return new double[] { //
				Math.atan2(z, y), //
				Math.atan2(x, z), //
				Math.atan2(y, x) //
		};
	}

	public Point project(Axis axis) {
		switch (axis) {
		case X:
			return new Point(0, this.getY(), this.getZ());
		case Y:
			return new Point(this.getX(), 0, this.getZ());
		case Z:
			return new Point(this.getX(), this.getY(), 0);
		default:
			return null;
		}
	}

	public double getLength() {
		return Math.sqrt(this.getLength2());
	}

	public double getLength2() {
		return Dist.dist2(this, new Point(0, 0, 0));
	}

	public void normalize() {
		final double len = this.getLength();
		this.multiply(1. / len);
	}

	public double[] getxyz() {
		return new double[] { this.x, this.y, this.z };
	}

	public static Point error() {
		System.out.println("Error Point created!");
		return new Point(Double.NaN, Double.NaN, Double.NaN);
	}

	public String export_obj() {
		return "v " + x + " " + y + " " + z + "\n";
	}

	public Point clone() {
		return new Point(this.getxyz());
	}

	public String print() {
		return (this.x + " " + this.y + " " + this.z);
	}
}
