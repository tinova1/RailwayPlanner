package common.vectorMath.objects2D;

import java.util.ArrayList;

import common.svgCreator.Tag;
import common.vectorMath.MathUtils;
import common.vectorMath.objects3D.Axis;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Circle extends Curve {
	protected Point c; // center point
	protected double r; // radius

	public Circle(Point center, double radius) {
		c = new Point(center.getX(), center.getY(), 0.);
		r = Math.abs(radius);
	}

	public Point getC() {
		return c;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public ArrayList<Point> getVerts(int res) {
		// res: resolution (number of verts)
		ArrayList<Point> verts = new ArrayList<Point>();
		double dphi = 2. * Math.PI / (double) res;
		for (double phi = 0; phi < 2. * Math.PI; phi += dphi) {
			Point vert = new Point(r, 0., 0.);
			vert.rotate(phi);
			verts.add(vert);
		}
		return verts;
	}

	public void move(Point dp) {
		this.c.move(dp);
	}

	public void rotate(final Point center, final double drot) {
		this.c.rotate(center, drot);
	}

	public void mirror(Axis axis) {
		this.c.mirror(axis);
	}

	public void mirrorX() {
		this.mirror(Axis.X);
	}

	public String export_obj() {
		String ausgabe = "";
		ArrayList<Point> verts = this.getVerts(48);
		for (int i = 0; i < verts.size() - 1; i++) {
			ausgabe += verts.get(i).export_obj();
			ausgabe += verts.get((i + 1) % verts.size()).export_obj();
			ausgabe += "f -1 -2\n";
		}
		return ausgabe;
	}

	public Tag export_svg() {
		final double cx = this.getC().getX();
		final double cy = this.getC().getY();
		final double r = this.getR();

		final Tag t = new Tag("circle");
		t.addEntry("cx", String.valueOf(cx));
		t.addEntry("cy", String.valueOf(cy));
		t.addEntry("r", String.valueOf(r));

		return t;
	}

	public Point[] intersection(Curve c) {
		if (c instanceof LineSeg) {
			return this.intersection((LineSeg) c);
		} else if (c instanceof Line) {
			return this.intersection((Line) c);
		} else if (c instanceof Arc) {
			return this.intersection((Arc) c);
		} else if (c instanceof Circle) {
			return this.intersection((Circle) c);
		} else if (c instanceof Path) {
			return c.intersection(this);
		} else if (c == null) {
			return new Point[] {};
		} else {
			System.out.println("Circle - intersection: unbekannter Curve-Typ: " + c.getClass() + " ");
			return null;
		}
	}

	private Point[] intersection(final Line line) {
		return line.intersection(this);
	}

	private Point[] intersection(final LineSeg seg) {
		return seg.intersection(this);
	}

	private Point[] intersection(final Arc arc) {
		final Point[] toTest = this.intersection((Circle) arc);
		ArrayList<Point> output = new ArrayList<>();
		for (int i = 0; i < toTest.length; i++) {
			if (arc.PointOnArc(toTest[i])) {
				output.add(toTest[i]);
			}
		}
		return output.toArray(new Point[output.size()]);
	}

	private Point[] intersection(final Circle circle) {
		final Point _A = this.getC();
		final Point _B = circle.getC();
		final double a = this.getR();
		final double b = circle.getR();
		final double ab0 = _B.getX() - _A.getX();
		final double ab1 = _B.getY() - _A.getY();
		final double c = MathUtils.pythargoras(ab0, ab1);
		final double x = (a * a + c * c - b * b) / (c * 2.);
		double y = a * a - x * x;
		if (y < 0) {
			// no intersection
			return new Point[] {};
		}
		if (y > 0) {
			y = Math.sqrt(y);
		}
		final double ex0 = ab0 / c;
		final double ex1 = ab1 / c;
		final double ey0 = -ex1;
		final double ey1 = ex0;
		double q1x = _A.getX() + x * ex0;
		double q1y = _A.getY() + x * ex1;
		if (y == 0) {
			return new Point[] { new Point(q1x, q1y, 0) };
		}
		final double q2x = q1x - y * ey0;
		final double q2y = q1y - y * ey1;
		q1x += y * ey0;
		q1y += y * ey1;
		final Point p1 = new Point(q1x, q1y, 0);
		final Point p2 = new Point(q2x, q2y, 0);
		if (q1x >= q1y)
			return new Point[] { p1, p2 };
		else
			return new Point[] { p2, p1 };

	}

	public Circle clone() {
		return new Circle(this.c.clone(), this.r);
	}

	public void trim(Curve c, boolean reverse) {
		System.out.println("Circle cannot be trimmed");
	}

	public String print() {
		return "Circle " + this.c.print() + " R " + this.r;
	}

	public double getLength() {
		return 2. * Math.PI * this.r;
	}

	public Circle getReverse() {
		System.out.println("Einen Kreis umzudrehen macht keinen Sinn");
		return this;
	}

	public double tangentAngle(Point p) {
		final double dx = p.getX() - this.c.getX();
		final double dy = p.getY() - this.c.getY();
		return Math.atan2(dy, dx);
	}

	public Point getStartPoint() {
		return null;
	}

	public Point getEndPoint() {
		return null;
	}

	public void setStartPoint(final Point p) {
		System.out.println("setStartPoint: ungültig bei Circle");
	}

	public void setEndPoint(final Point p) {
		System.out.println("setEndPoint: ungültig bei Circle");
	}

}
