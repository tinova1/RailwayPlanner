package common.vectorMath.objects2D;

import java.util.ArrayList;

import common.svgCreator.Tag;
import common.vectorMath.Dist;
import common.vectorMath.MathUtils;
import common.vectorMath.RotDir;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects3D.Axis;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Arc extends Circle {
	// start and end angles
	private double st;
	private double en;

	private final RotDir rotDir; // ccw or cw

	public Arc(Point center, double radius, double start, double end, final RotDir rotDir) {
		super(center, radius);

		this.rotDir = rotDir;
		this.st = start;
		this.en = end;
	}

	public Arc(Circle circle, double start, double end, final RotDir rotDir) {
		super(circle.getC(), circle.getR());
		this.rotDir = rotDir;
		this.st = start;
		this.en = end;
	}

	public Arc(Point center, Point start, Point end, RotDir rotDir) {
		super(center, 1);
		this.rotDir = rotDir;
		this.setStartPoint(start);
		this.setEndPoint(end);
	}

	public double getStart() {
		return this.st;
	}

	public double getEnd() {
		return this.en;
	}

	private void setStart(final double start) {
		this.st = start;
	}

	private void setEnd(final double end) {
		this.en = end;
	}

	public double getLength() {
		return Math.abs(MathUtils.toLength(en - st, r));
	}

	public double getArcAngle() {
		return Math.abs(en - st);
	}

	public RotDir getRotDir() {
		return this.rotDir;
	}

	public Point startPoint() {
		final double x = r * Math.cos(st) + c.getX();
		final double y = r * Math.sin(st) + c.getY();
		return new Point(x, y);
	}

	public Point getEndPoint() {
		final double x = r * Math.cos(en) + c.getX();
		final double y = r * Math.sin(en) + c.getY();
		return new Point(x, y);
	}

	public void setStartPoint(final Point start) {
		this.r = Dist.dist(start, this.c);
		this.setStart(Math.atan2(-this.c.getY() + start.getY(), -this.c.getX() + start.getX()));
	}

	public void setEndPoint(final Point end) {
		this.setEnd(Math.atan2(-this.c.getY() + end.getY(), -this.c.getX() + end.getX()));
	}

	public Arc getReverse() {
		final RotDir reverse;
		if (this.rotDir == RotDir.POS) {
			reverse = RotDir.NEG;
		} else {
			reverse = RotDir.POS;
		}
		return new Arc(this.c, this.getEndPoint(), this.startPoint(), reverse);
	}

	public boolean PointOnArc(final Point p1) {
		final Point p = VectorUtils.subtract(p1, this.c);
		final double angle = p.getAngle()[2];
		final double st = MathUtils.absAngle(this.st);
		final double en = MathUtils.absAngle(this.en);

		double diffAngleSt = MathUtils.diffAngle(angle, st);
		if (diffAngleSt < 0)
			diffAngleSt += 2. * Math.PI;
		double diffEnSt = MathUtils.diffAngle(en, st);
		if (diffEnSt < 0)
			diffEnSt += 2. * Math.PI;

		if (this.rotDir == RotDir.POS) {
			return diffAngleSt <= diffEnSt;
		} else { // rotDir == RotDir.NEG
			return diffAngleSt >= diffEnSt;
		}
	}

	public void rotate(final Point center, final double drot) {
		this.c.rotate(center, drot);
		this.st += drot;
		this.en += drot;
	}

	public void mirrorX() {
		this.c.mirror(Axis.X);
		final double z = this.st;
		this.st = 2. * Math.PI - this.en;
		this.en = 2. * Math.PI - z;
	}

	public ArrayList<Point> getVerts(int res) {
		// res: resolution (number of verts in one circle)
		ArrayList<Point> verts = new ArrayList<Point>();
		double dphi = 2. * Math.PI / (double) res;
		for (double phi = st; phi < en; phi += dphi) {
			Point vert = new Point(r, 0., 0.);
			vert.rotate(phi);
			verts.add(vert);
		}
		Point lastvert = new Point(r, 0., 0.);
		lastvert.rotate(en);
		verts.add(lastvert);
		return verts;
	}

	public Tag export_svg() {
		final Path p = new Path(this);
		return p.export_svg();
	}

	public Point[] intersection(Curve c) {
		if (c instanceof LineSeg) {
			return intersection((LineSeg) c);
		} else if (c instanceof Line) {
			return intersection((Line) c);
		} else if (c instanceof Arc) {
			return intersection((Arc) c);
		} else if (c instanceof Circle) {
			return intersection((Circle) c);
		} else if (c instanceof Path) {
			return c.intersection(this);
		} else {
			System.out.println("Circle - intersection: unbekannte Curve-Typ");
			return null;
		}
	}

	public Point[] intersection(Line l) {
		return l.intersection(this);
	}

	public Point[] intersection(LineSeg l) {
		return l.intersection(this);
	}

	public Point[] intersection(Circle c) {
		return c.intersection(this);
	}

	public Point[] intersection(Arc arc) {
		Circle c = new Circle(this.c, this.r);
		final Point[] toTest = c.intersection((Circle) arc);
		ArrayList<Point> output = new ArrayList<>();
		for (int i = 0; i < toTest.length; i++) {
			final Point p = toTest[i];
			if (this.PointOnArc(p) && arc.PointOnArc(p)) {
				output.add(p);
			}
		}
		return output.toArray(new Point[output.size()]);
	}

	public Arc clone() {
		return new Arc((Circle) this, this.st, this.en, this.rotDir);
	}

	public Arc trimClone(final Curve c, final boolean inverse) {
		final Point[] inters = this.intersection(c);
		if (inters.length > 0) {
			Arc arc = this.clone();
			if (!inverse) {
				arc.setEndPoint(inters[0]);
			} else {
				arc.setStartPoint(inters[0]);
			}
			return arc;
		} else {
			return this.clone();
		}
	}

	public String print() {
		return "M " + c.print() + " St " + this.startPoint().print() + " En " + this.getEndPoint().print() + "\n";
	}
}
