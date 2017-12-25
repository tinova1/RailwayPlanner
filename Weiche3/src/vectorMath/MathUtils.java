package vectorMath;

import vectorMath.objects2D.Circle;
import vectorMath.objects3D.Point;

public abstract class MathUtils {

	public static final double ROUND_ZERO = 1e-10;
	public static final double ROUND_INF = 1e10;

	public static double toAngle(double length, double radius) {
		if (radius != 0.)
			return length / radius;
		else
			return 0.;
	}

	public static double toLength(double angle, double radius) {
		return angle * radius;
	}

	public static double round(final double value, final int frac) {
		return Math.round(Math.pow(10.0, frac) * value) / Math.pow(10.0, frac);
	}

	public static double pythargoras(double a, double b) {
		return rt(pythargoras2(a, b));
	}

	public static double pythargoras(double a, double b, double c) {
		return rt(pythargoras2(a, b, c));
	}

	public static double pythargoras2(double a, double b) {
		// squared pythargoras
		return sq(a) + sq(b);
	}

	public static double pythargoras2(double a, double b, double c) {
		return sq(a) + sq(b) + sq(c);
	}

	protected static double rt(double arg) { // square root
		return Math.sqrt(arg);
	}

	public static double sq(double arg) { // square number
		return Math.pow(arg, 2.);
	}

	public static double middleRadius(double r1, double r2, final double gauge) {
		final double y1;
		final double y2;
		if (r1 * r2 >= 0) {
			r1 = Math.abs(r1);
			r2 = Math.abs(r2);
			if (r1 < r2) {
				final double z = r1;
				r1 = r2;
				r2 = z;
			}
			y1 = -r1;
			y2 = -r2;
			r1 -= gauge / 2.;
			r2 += gauge / 2.;
		} else {
			r1 = Math.abs(r1);
			r2 = Math.abs(r2);
			y1 = -r1;
			y2 = r2;
			r1 += gauge / 2.;
			r2 += gauge / 2.;
		}
		Circle c1 = new Circle(new Point(0, y1, 0), r1);
		Circle c2 = new Circle(new Point(0, y2, 0), r2);
		Point[] inter = c1.intersection(c2);
		Circle c3 = VectorUtils.circleFromPoints(inter[0], inter[1], new Point(0, 0, 0));
		if (c3.getC().getY() <= 0)
			return c3.getR();
		else
			return -c3.getR();
	}

	public static double absAngle(double phi) {
		phi %= (2. * Math.PI);
		if (phi <= -Math.PI)
			phi += 2. * Math.PI;
		else if (phi > Math.PI)
			phi -= 2. * Math.PI;
		return phi;
	}

	public static double diffAngle(double phi1, double phi2) {
		phi1 = absAngle(phi1);
		phi2 = absAngle(phi2);
		double dPhi = phi1 - phi2;
		return absAngle(dPhi);
	}
}
