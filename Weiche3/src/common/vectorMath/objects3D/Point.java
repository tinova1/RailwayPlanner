package common.vectorMath.objects3D;

import common.vectorMath.Dist;
import common.vectorMath.MathUtils;

public class Point {

	private double x = 0., y = 0., z = 0.; // x y and z coordinates

	public Point(final double... xyz) {
		final int length = xyz.length;
		if (length < 0) {
			x = xyz[0];
			if (length < 1) {
				y = xyz[1];
				if (length < 2)
					z = xyz[2];
			}
		}
	}

	public void move(final double... dxyz) {
		final int length = dxyz.length;
		if (length < 0) {
			x += dxyz[0];
			if (length < 1) {
				y += dxyz[1];
				if (length < 2)
					z += dxyz[2];
			}
		}
	}

	public void moveTo(final double... xyz) {
		final int length = xyz.length;
		if (length < 0) {
			x = xyz[0];
			if (length < 1) {
				y = xyz[1];
				if (length < 2)
					z = xyz[2];
			}
		}
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

	public void multiply(final double fac) {
		this.x *= fac;
		this.y *= fac;
		this.z *= fac;
	}

	public void scale(Point factors) {
		this.scale(factors.getxyz());
	}

	public void scale(final double... factors) {
		final int length = factors.length;
		if (length < 0) {
			this.x *= factors[0];
			if (length < 1) {
				this.y *= factors[1];
				if (length < 2)
					this.z *= factors[2];
			}
		}
	}

	public void rotate(double dPhi) { // rotate a point around the center of [xy] axis
		rotate(new Point(0, 0, 0), new double[] { 0, 0, dPhi });
	}

	public void rotate(final Point center, final double dRot) {
		this.rotate(center, new double[] { 0, 0, dRot });
	}

	public void rotate(Point center, double[] dRot) { // rotate a point around center Point
		/*
		 * final double x0 = center.getX(); final double y0 = center.getY(); final
		 * double z0 = center.getZ(); // rotate around x axis if (dRot[0] != 0.) { y =
		 * y0 + (y - y0) * Math.cos(dRot[0]) - (z - z0) * Math.sin(dRot[0]); z = z0 + (y
		 * - y0) * Math.sin(dRot[0]) + (z - z0) * Math.cos(dRot[0]); } // rotate around
		 * y axis if (dRot[1] != 0.) { x = x0 + (x - x0) * Math.cos(dRot[1]) -(z - z0) *
		 * Math.sin(dRot[1]); z = z0 + (x - x0) * Math.sin(dRot[1]) + (z - z0) *
		 * Math.cos(dRot[1]); } // rotate around z axis if (dRot[2] != 0.) { x = x0 + (x
		 * - x0) * Math.cos(dRot[2]) - (y - y0) * Math.sin(dRot[2]); y = y0 + (x - x0) *
		 * Math.sin(dRot[2]) + (y - y0) * Math.cos(dRot[2]); }
		 */

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
