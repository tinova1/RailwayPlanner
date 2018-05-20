package common.vectorMath.objects3D;

import java.util.ArrayList;

import common.vectorMath.Dist;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Circle;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;

//a Line Segment with defined end and start points

public class LineSeg extends Line {

	public LineSeg(Point pos1, Point pos2) {
		super(pos1, pos2);
	}

	public LineSeg(Point p, double angle, double length) {
		super(p, angle);
		final Point direction = this.getDir();
		direction.multiply(length);
		this.endPoint = VectorUtils.add(this.startPoint, direction);
	}

	public Point getMiddle() {
		return VectorUtils.middle(this.startPoint, this.endPoint);
	}

	public double getLength() {
		return this.getDir().getLength();
	}

	public boolean pointOnSeg(Point p) {
		if (p == null) {
			return false;
		} else {
			Point middle = this.getMiddle();
			double maxDist = this.getDir().getLength() / 2.;
			// max distance between middle of LineSeg and intersection Point
			return Dist.dist(p, middle) <= maxDist;
		}
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
		}
		System.out.println("LineSeg - intersection: unbekannter Curve-Typ");
		return null;
	}

	public Point[] intersection(Circle c) {
		Line l = new Line(this.startPoint, this.endPoint);
		Point[] toTest = l.intersection(c);
		ArrayList<Point> output = new ArrayList<>();
		for (int i = 0; i < toTest.length; i++) {
			final Point p = toTest[i];
			if (this.pointOnSeg(p)) {
				output.add(p);
			}
		}
		return output.toArray(new Point[output.size()]);
	}

	public Point[] intersection(Arc arc) {
		// intersection point to test whether on LineSeg
		Line l = new Line(this.startPoint, this.endPoint);
		Point[] toTest = l.intersection(arc);
		ArrayList<Point> output = new ArrayList<>();
		for (int i = 0; i < toTest.length; i++) {
			final Point p = toTest[i];
			if (this.pointOnSeg(p)) {
				output.add(p);
			}
		}
		return output.toArray(new Point[output.size()]);
	}

	public Point[] intersection(Line l) {
		return l.intersection(this);
	}

	public Point[] intersection(LineSeg s) {
		Line l = new Line(this.startPoint, this.endPoint);
		Point[] toTest = l.intersection((Line) s); // Schnittpunkt beider Geraden
		if (toTest.length > 0 && this.pointOnSeg(toTest[0]) && s.pointOnSeg(toTest[0]))
			return new Point[] { toTest[0] };
		else
			return new Point[] {};
	}

	public LineSeg clone() {
		return new LineSeg(this.startPoint.clone(), this.endPoint.clone());
	}

	public LineSeg getReverse() {
		return new LineSeg(this.endPoint, this.startPoint);
	}

	public LineSeg trimClone(final Curve c, final boolean inverse) {
		final Point[] inters = this.intersection(c);
		if (inters.length > 0) {
			LineSeg l = this.clone();
			if (!inverse) {
				l.setEndPoint(inters[0]);
			} else {
				l.setStartPoint(inters[0]);
			}
			return l;
		} else {
			return this.clone();
		}
	}
}
