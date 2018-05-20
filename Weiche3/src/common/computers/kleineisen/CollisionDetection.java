package common.computers.kleineisen;

import common.vectorMath.Dist;
import common.vectorMath.Orientation;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects3D.Cube;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public abstract class CollisionDetection {

	public static boolean pointInCube(final Point point, final Cube cube) {
		final Point center = cube.getCenter(Orientation.GLOBAL);

		// Pre-Testing
		if (Dist.dist2(point, center) >= cube.getDiagonal2() / 4.)
			return false;

		// local directions of csys of cube
		final Point xDir = cube.getCSYS().getDirs()[0];
		final Point yDir = cube.getCSYS().getDirs()[1];
		final Point zDir = cube.getCSYS().getDirs()[2];

		final double xScale = cube.getCSYS().getScale()[0];
		final double yScale = cube.getCSYS().getScale()[1];
		final double zScale = cube.getCSYS().getScale()[2];

		return Dist.projDist(center, point, xDir) < xScale && //
				Dist.projDist(center, point, yDir) < yScale && //
				Dist.projDist(center, point, zDir) < zScale;

		/*
		 * Point p = point.clone(); Cube c = cube.clone(); double rot = c.getZRot();
		 * c.getCSYS().rotate(new double[] { 0, 0, -rot }); p.rotate(-rot); Point dist =
		 * VectorUtils.subtract(p, c.getCSYS().getPoint()); if (Math.abs(dist.getX()) <=
		 * c.getDimensions().getX() / 2. && Math.abs(dist.getY()) <=
		 * c.getDimensions().getY() / 2. && Math.abs(dist.getZ()) <=
		 * c.getDimensions().getZ() / 2.) { return true;
		 * 
		 * } return false;
		 */
	}

	public static boolean cubeInCube(Cube c, Cube d) {
		final Orientation GLOBAL = Orientation.GLOBAL;
		final Point center = c.getCenter(Orientation.GLOBAL);
		if (pointInCube(center, d))
			return true;
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
