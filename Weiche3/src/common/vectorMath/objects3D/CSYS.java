package common.vectorMath.objects3D;

import common.vectorMath.Dist;
import common.vectorMath.MathUtils;
import common.vectorMath.RotDir;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;

public class CSYS {
	private Point p = new Point(0, 0, 0);
	private double[] rot = { 0d, 0d, 0d };
	private double[] scale = { 1d, 1d, 1d };

	public CSYS() {

	}

	public CSYS(Point p, final double[] scale, final double[] rot) {
		this.p = p;
		this.scale = scale;
		this.rot = rot;
	}

	public Point getPoint() {
		return this.p;
	}

	public double getX() {
		return this.p.getX();
	}

	public double getY() {
		return this.p.getY();
	}

	public double getZ() {
		return this.p.getZ();
	}

	public Line[] getAxis() {
		Point[] directions = { //
				new Point(1, 0, 0), //
				new Point(0, 1, 0), //
				new Point(0, 0, 1) };
		Line[] output = new Line[3];
		for (int i = 0; i < output.length; i++) {
			directions[i].scale(this.scale);
			directions[i].rotate(new Point(0, 0, 0), this.rot);
			output[i] = new Line(this.p, VectorUtils.add(this.p, directions[i]));
		}
		return output;
	}

	public void scale(final double[] scale) {
		for (int i = 0; i < this.scale.length; i++) {
			this.scale[i] *= scale[i];
		}
	}

	public double[] getScale() {
		return this.scale;
	}

	public void move(final double dx, final double dy, final double dz) {
		this.p.move(dx, dy, dz);
	}

	public void move(final double[] dxyz) {
		this.p.move(dxyz);
	}

	public void move(final Point dp) {
		this.p.move(dp);
	}

	public void move(final double dist, final double angle) {
		this.p.move(dist, angle);
	}

	public void rotate(final double[] drot) {
		for (int i = 0; i < this.rot.length; i++) {
			this.rot[i] += drot[i];
		}
		this.p.rotate(rot[2]);
	}

	public void rotate(final Point center, final double[] dRot) {
		this.p.rotate(center, dRot);
		for (int i = 0; i < this.rot.length; i++) {
			this.rot[i] += dRot[i];
		}
	}

	public void rotate(final Point center, final double length) {
		double radius = Dist.dist(center, this.p);
		if (Math.abs(radius) > MathUtils.ROUND_INF) {
			this.move(-length, 0, 0);
		} else if (radius > MathUtils.ROUND_ZERO) {
			double angle = MathUtils.toAngle(length, radius);
			this.rotate(center, new double[] { 0, 0, angle });
		}
	}

	public double[] getRot() {
		return this.rot;
	}

	public void mirrorX() {
		this.p.mirror(Axis.X);
		this.rot[2] *= -1.; // rotate Z axis
		this.scale[1] *= -1.; // scale Y axis
	}

	public void moveAlong(final Path p, double length, final boolean rotate) {
		// rotate: should x-axis of CSYS be rotated to be tangent before moving along
		// path?
		if (rotate && p.getCurves().size() > 0) {
			final Curve first = p.getCurves().get(0);
			if (first instanceof Arc) {
				this.rot[2] = ((Arc) first).getStart() + Math.PI / 2.;
			} else if (first instanceof Line) {
				this.rot[2] = ((Line) first).getAngle();
			}
		}
		for (int i = 0; i < p.getCurves().size() && length > 0.; i++) {
			final Curve c = p.getCurves().get(i);
			if (c instanceof Arc) {
				final Arc arc = (Arc) c;
				final double angleLen = MathUtils.toAngle(length, arc.getR());
				final double angleMax = Math.abs(arc.getArcAngle());
				double angle = Math.min(angleLen, angleMax);
				if(arc.getRotDir()==RotDir.NEG) {
					angle*=-1.;
				}
				this.rotate(arc.getC(), new double[] { 0, 0, angle });
				length -= MathUtils.toLength(Math.abs(angle), arc.getR());
			} else if (c instanceof LineSeg) {
				final double lengthMax = ((LineSeg) c).getDir().getLength();
				final double length1 = Math.min(lengthMax, length);
				this.move(length1, ((LineSeg) c).getAngle());
				length -= length1;
			} else {
				System.out.println("MoveAlong: unbekannter Curve-Typ");
			}
		}
	}

}
