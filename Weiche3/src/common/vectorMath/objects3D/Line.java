package common.vectorMath.objects3D;

import java.util.ArrayList;

import common.svgCreator.Entry;
import common.svgCreator.Tag;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Circle;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;

public class Line extends Curve {
	protected Point startPoint;
	protected Point endPoint;
	protected Point direction;

	protected double a;
	protected double b;
	protected double c;

	public Line(Point p1, Point p2) {
		this.startPoint = p1;
		this.endPoint = p2;
		updateDir();
		updateABC();
	}

	public Line(Point p, double angle) {
		this.startPoint = p;
		direction = new Point(Math.cos(angle), Math.sin(angle), 0);
		this.endPoint = VectorUtils.add(startPoint, direction);
		updateDir();
		updateABC();
	}

	// Koordinatenform ax+by=c in der XY-Ebene
	public Line(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.direction = new Point(-b, a, 0);
		if (a != 0.) {
			this.startPoint = new Point(c / a, 0, 0);
		} else {
			this.startPoint = new Point(0, c / b, 0);
		}
		this.endPoint = VectorUtils.add(startPoint, direction);
	}

	private void updateABC() {
		a = startPoint.getY() - endPoint.getY();
		b = endPoint.getX() - startPoint.getX();
		c = endPoint.getX() * startPoint.getY() - startPoint.getX() * endPoint.getY();
	}

	private void updateDir() {
		direction = VectorUtils.subtract(this.endPoint, this.startPoint);
	}

	public Point getDir() {
		return direction;
	}

	public Point getNormDir() {
		final Point direction = this.direction.clone();
		direction.normalize();
		return direction;
	}

	public Point startPoint() {
		return startPoint;
	}

	public void setStartPoint(Point p) {
		this.startPoint = p;
		this.updateABC();
		this.updateDir();
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(final Point p) {
		this.endPoint = p;
		this.updateABC();
		this.updateDir();
	}

	public double geta() {
		return a;
	}

	public double getb() {
		return b;
	}

	public double getc() {
		return c;
	}

	public double getAngle() {
		return Math.atan2(direction.getY(), direction.getX());
	}

	public String print() {
		return "P1 " + startPoint.print() + " P2 " + endPoint.print() + "\n";
	}

	public ArrayList<Point> getVerts() {
		ArrayList<Point> ausgabe = new ArrayList<Point>();
		ausgabe.add(startPoint());
		ausgabe.add(getEndPoint());
		return ausgabe;
	}

	public String export_obj() {
		String ausgabe = "";
		ausgabe += "Line\n";
		ausgabe += startPoint.export_obj();
		ausgabe += endPoint.export_obj();
		ausgabe += "f -1 -2\n";
		return ausgabe;
	}

	public Tag export_svg() {
		final double x1 = this.startPoint().getX();
		final double y1 = this.startPoint().getY();
		final double x2 = this.getEndPoint().getX();
		final double y2 = this.getEndPoint().getY();

		final Tag tag = new Tag("line", true);
		tag.addEntry(new Entry("x1", String.valueOf(x1)));
		tag.addEntry(new Entry("y1", String.valueOf(y1)));
		tag.addEntry(new Entry("x2", String.valueOf(x2)));
		tag.addEntry(new Entry("y2", String.valueOf(y2)));

		return tag;
	}

	public Point[] intersection(Curve curve) {
		Path path = new Path(curve);
		final ArrayList<Point[]> inters = new ArrayList<>();
		for (Curve i : path.getCurves()) {
			if (i instanceof Arc) {
				inters.add(this.intersection((Arc) i));
			} else if (i instanceof LineSeg) {
				inters.add(this.intersection((LineSeg) i));
			} else if (i instanceof Circle) {
				inters.add(this.intersection((Circle) i));
			} else if (i instanceof Line) {
				inters.add(this.intersection((Line) i));
			} else {
				System.out.println("intersection: unzulässiger Curve-Typ");
				break;
			}
		}
		final ArrayList<Point> output = new ArrayList<>();
		for (Point[] a : inters)
			for (Point p : a)
				output.add(p);

		return output.toArray(new Point[output.size()]);
	}

	public Point[] intersection(final Arc arc) {
		// intersections between line and circle, to test if it line crosses arc
		ArrayList<Point> ausgabe = new ArrayList<Point>();
		final Point[] inters = this.intersection((Circle) arc);
		for (int i = 0; i < inters.length; i++) {
			final Point p = inters[i];
			if (arc.PointOnArc(p)) {
				ausgabe.add(p);
			}
		}
		return ausgabe.toArray(new Point[ausgabe.size()]);
	}

	public Point[] intersection(Circle circle) {
		// Bezeichnungen siehe Wikipedia
		double a = this.geta();
		double b = this.getb();
		double c = this.getc();
		double x0 = circle.getC().getX();
		double y0 = circle.getC().getY();
		double r = circle.getR();

		double d = c - a * x0 - b * y0;

		double x1 = x0 + (a * d + b * rt(sq(r) * (sq(a) + sq(b)) - sq(d))) / (sq(a) + sq(b));
		double x2 = x0 + (a * d - b * rt(sq(r) * (sq(a) + sq(b)) - sq(d))) / (sq(a) + sq(b));
		double y1 = y0 + (b * d - a * rt(sq(r) * (sq(a) + sq(b)) - sq(d))) / (sq(a) + sq(b));
		double y2 = y0 + (b * d + a * rt(sq(r) * (sq(a) + sq(b)) - sq(d))) / (sq(a) + sq(b));

		if (Double.isFinite(x1)) {
			return new Point[] { (new Point(x1, y1, 0.)), (new Point(x2, y2, 0.)) };
		} else {
			return new Point[] {};
		}
	}

	public Point[] intersection(Line line) {
		double a1 = this.geta();
		double b1 = this.getb();
		double c1 = this.getc();
		double a2 = line.geta();
		double b2 = line.getb();
		double c2 = line.getc();

		// Schnittpunkt
		double xs = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
		double ys = (a1 * c2 - a2 * c1) / (a1 * b2 - a2 * b1);
		if (Double.isFinite(xs)) {
			return new Point[] { (new Point(xs, ys, 0)) };
		} else {
			return new Point[] {};
		}
	}

	// s = Strecke
	public Point[] intersection(LineSeg s) {
		Point[] toTest = intersection((Line) s); // Schnittpunkt beider Geraden
		if (toTest.length > 0 && s.pointOnSeg(toTest[0]))
			return new Point[] { toTest[0] };
		else
			return new Point[] {};
	}

	public void mirrorX() {
		this.mirror(Axis.X);
	}

	public void mirror(Axis axis) {
		this.startPoint.mirror(axis);
		this.endPoint.mirror(axis);
		this.updateABC();
		this.updateDir();
	}

	public void move(Point dp) {
		this.startPoint.move(dp);
		this.endPoint.move(dp);
		this.updateABC();
	}

	public void move(final double dist, final double angle) {
		this.startPoint.move(dist, angle);
		this.endPoint.move(dist, angle);
		this.updateABC();
	}

	public void rotate(Point center, double drot) {
		this.startPoint.rotate(center, drot);
		this.endPoint.rotate(center, drot);
		this.updateABC();
		this.updateDir();
	}

	public Line clone() {
		return new Line(this.startPoint.clone(), this.endPoint.clone());
	}

	public double getLength() {
		return Double.POSITIVE_INFINITY;
	}

	public void trim(Curve c, boolean reverse) {
		System.out.println("Line cannot be trimmed");
	}

	public Line getReverse() {
		System.out.println("Eine Linie umzudrehen macht keinen Sinn");
		return this;
	}
}
