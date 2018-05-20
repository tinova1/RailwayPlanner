package common.vectorMath;

import java.util.ArrayList;
import java.util.List;

import common.vectorMath.objects2D.Circle;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Plane;
import common.vectorMath.objects3D.Point;

public abstract class Dist {
	public static double dist(Point p, Line l) {
		Point a = l.getStartPoint();
		Point b = l.getDir();
		return Math.abs(VectorUtils.crossP(VectorUtils.subtract(p, a), b).getLength() / b.getLength());
	}

	public static double dist(Line l, Point p) {
		return dist(p, l);
	}

	public static double dist(Point p1, Point p2) {
		return Math.sqrt(dist2(p1, p2));
	}

	public static double distInXYPlane2(Point p1, Point p2) {
		return MathUtils.pythargoras2(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}

	public static double dist2(Point p1, Point p2) {
		final double x1 = p1.getX();
		final double y1 = p1.getY();
		final double z1 = p1.getZ();
		final double x2 = p2.getX();
		final double y2 = p2.getY();
		final double z2 = p2.getZ();
		return MathUtils.pythargoras2(x2 - x1, y2 - y1, z2 - z1);
	}

	public static double dist(Line l1, Line l2) {
		if (VectorUtils.linDep(l2.getDir(), l1.getDir())) {
			return dist(l1, l2.getStartPoint());
		} else {
			Point a1 = l1.getDir();
			Point a2 = l2.getDir();
			Point r1 = l1.getStartPoint();
			Point r2 = l2.getStartPoint();
			final double nom = Math.abs(VectorUtils.scalar(VectorUtils.crossP(a1, a2), VectorUtils.subtract(r1, r2)));
			final double denom = Math.abs(VectorUtils.crossP(a1, a2).getLength());
			return nom / denom;
		}
	}

	public static double dist(Line l, Circle c) {
		return dist(c.getC(), l) - c.getR();
	}

	public static double dist(Line l, LineSeg s) {
		if (l.intersection(s) != null) {
			return 0.;
		} else {
			final double d1 = dist(l, s.getStartPoint());
			final double d2 = dist(l, s.getEndPoint());
			return Math.min(d1, d2);
		}
	}

	public static double dist(Plane pl, Point p) {
		// Ergebnis hat Vorzeichen!
		final double a = pl.getA();
		final double b = pl.getB();
		final double c = pl.getC();
		final double d = pl.getD();
		final double x = p.getX();
		final double y = p.getY();
		final double z = p.getZ();
		return (a * x + b * y + c * z - d);
	}

	public static double projDist(Point p1, Point p2, Point vector) {
		final Plane pl = new Plane(p1, vector);
		return Math.abs(dist(pl, p2));
	}

	public static double projDist(Point p, Point vector, Curve c) {
		final List<Double> distances = new ArrayList<>();
		final Line connection = new Line(p, VectorUtils.add(p, vector));
		final Point[] inters = connection.intersection(c);
		for (Point inter : inters) {
			final double projDist2 = dist2(p, inter);
			if (Double.isFinite(projDist2))
				distances.add(dist2(p, inter));
		}
		double minDist = Double.POSITIVE_INFINITY;
		for (double i : distances)
			minDist = Math.min(i, minDist);
		return Math.sqrt(minDist);
	}
}
