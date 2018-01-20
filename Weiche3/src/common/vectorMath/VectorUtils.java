package common.vectorMath;

import common.vectorMath.objects2D.Circle;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.Point;

public abstract class VectorUtils extends MathUtils {

	public static double scalar(Point p1, Point p2) {
		double a1 = p1.getX();
		double a2 = p1.getY();
		double a3 = p1.getZ();
		double b1 = p2.getX();
		double b2 = p2.getY();
		double b3 = p2.getZ();
		return a1 * b1 + a2 * b2 + a3 * b3;
	}

	public static Point crossP(Point p1, Point p2) {
		double x = p1.getY() * p2.getZ() - p2.getY() * p1.getZ();
		double y = p1.getZ() * p2.getX() - p2.getZ() * p1.getX();
		double z = p1.getX() * p2.getY() - p2.getX() * p1.getY();

		return new Point(x, y, z);
	}

	public static Point add(Point p1, Point p2) {
		double x = p2.getX() + p1.getX();
		double y = p2.getY() + p1.getY();
		double z = p2.getZ() + p1.getZ();
		return new Point(x, y, z);
	}

	public static Point subtract(Point p1, Point p2) {
		double x = p1.getX() - p2.getX();
		double y = p1.getY() - p2.getY();
		double z = p1.getZ() - p2.getZ();
		return new Point(x, y, z);
	}

	public static Point multiply(Point p1, double factor) {
		double x = p1.getX() * factor;
		double y = p1.getY() * factor;
		double z = p1.getZ() * factor;
		return new Point(x, y, z);
	}

	public static Point middle(Point p1, Point p2) {
		return VectorUtils.multiply(VectorUtils.add(p1, p2), .5);
	}

	public static double getAngle(Point a, Point b) {
		return Math.acos(VectorUtils.scalar(a, b) / (a.getLength() * b.getLength()));
	}

	public static Circle middleCircle(Circle c1, Line l) {

		Point m1 = c1.getC();
		double m1_l = Dist.dist(m1, l);
		double c1_l = m1_l - c1.getR();
		double line_angle = (l.getDir()).getAngle()[2];

		Point m_new = m1.clone(); // center of new circle
		double delta = c1_l / 2. - c1.getR();
		m_new.move(delta, line_angle + Math.PI / 2.);
		if (Dist.dist(m_new, l) < m1_l) {
			m_new.move(2. * delta, line_angle - Math.PI / 2.);
		}

		double r_new = 2. * c1.getR();

		return new Circle(m_new, r_new);
	}

	public static boolean equals(Point p1, Point p2) {
		for (int i = 0; i < p1.getxyz().length; i++) {
			if (Math.abs(p1.getxyz()[i] - p2.getxyz()[i]) > 1e-13) {
				return false;
			}
		}
		return true;
	}

	public static boolean linDep(Point p1, Point p2) {
		double k = p1.getLength() / p2.getLength();
		if (Double.isFinite(k) && k != 0.) {
			return equals(p1, multiply(p2, k)) || equals(p1, multiply(p2, -k));
		} else {
			return false;
		}
	}

	public static boolean isOrtho(Point p1, Point p2) {
		return Math.abs(scalar(p1, p2)) < ROUND_ZERO;
	}

	public static Circle circleFromPoints(final Point p1, final Point p2, final Point p3) {
		final double offset = Math.pow(p2.getX(), 2) + Math.pow(p2.getY(), 2);
		final double bc = (Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2) - offset) / 2.0;
		final double cd = (offset - Math.pow(p3.getX(), 2) - Math.pow(p3.getY(), 2)) / 2.0;
		final double det = (p1.getX() - p2.getX()) * (p2.getY() - p3.getY())
				- (p2.getX() - p3.getX()) * (p1.getY() - p2.getY());

		if (Math.abs(det) < ROUND_ZERO) {
			return new Circle(new Point(0, 0, 0), Double.POSITIVE_INFINITY);
		}

		final double idet = 1 / det;

		final double centerx = (bc * (p2.getY() - p3.getY()) - cd * (p1.getY() - p2.getY())) * idet;
		final double centery = (cd * (p1.getX() - p2.getX()) - bc * (p2.getX() - p3.getX())) * idet;
		final double radius = Math.sqrt(Math.pow(p2.getX() - centerx, 2) + Math.pow(p2.getY() - centery, 2));

		return new Circle(new Point(centerx, centery, 0), radius);
	}

	public static double[] toPolar(final double x, final double y, final Point center) {
		return toPolar(x, y, center.getX(), center.getY());
	}

	public static double[] toPolar(double x, double y, final double xC, final double yC) {
		x -= xC;
		y -= yC;
		final double phi = Math.atan2(y, x);
		final double r = MathUtils.pythargoras(x, y);
		return new double[] { r, phi };
	}

	public static double[] toCartesian(final double r, final double phi, final Point center) {
		final double x = r * Math.cos(phi) + center.getX();
		final double y = r * Math.sin(phi) + center.getX();
		return new double[] { x, y };
	}
}
