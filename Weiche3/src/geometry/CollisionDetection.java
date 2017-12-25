package geometry;

import vectorMath.Dist;
import vectorMath.Orientation;
import vectorMath.VectorUtils;
import vectorMath.objects2D.Curve;
import vectorMath.objects3D.Cube;
import vectorMath.objects3D.LineSeg;
import vectorMath.objects3D.Point;

public abstract class CollisionDetection {

	public static boolean pointInCube(Point point, Cube cube) {
		// Pre-Testing
		if (Dist.dist(point, cube.getCSYS().getPoint()) > cube.getDiagonal2() / 4.)
			return false;
		Point p = point.clone();
		Cube c = cube.clone();
		double rot = c.getZRot();
		c.getCSYS().rotate(new double[] { 0, 0, -rot });
		p.rotate(-rot);
		Point dist = VectorUtils.subtract(p, c.getCSYS().getPoint());
		if (Math.abs(dist.getX()) <= c.getDimensions().getX() / 2.
				&& Math.abs(dist.getY()) <= c.getDimensions().getY() / 2.
				&& Math.abs(dist.getZ()) <= c.getDimensions().getZ() / 2.) {
			return true;

		}
		return false;
	}

	public static boolean cubeInCube(Cube c, Cube d) {
		final Orientation GLOBAL = Orientation.GLOBAL;
		for (int i = 0; i < c.getVerts(GLOBAL).size(); i++) {
			if (pointInCube(c.getVerts(GLOBAL).get(i), d)) {
				return true;
			}
		}
		return false;
	}

	public static boolean polyInCurve(LineSeg[] poly, Curve c) {
		for (int i = 0; i < poly.length; i++) {
			if (poly[i].intersection(c).length > 0) {
				return true;
			}
		}
		return false;
	}

}
