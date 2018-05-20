package dddEngine;

import common.vectorMath.Dist;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Point;

public class Calculator {
	private Screen screen;
	public Calculator(final Screen screen) {
		this.screen=screen;
	}
	/*
	 * private static void setStuff(double[] ViewFrom, double[] ViewTo, double x,
	 * double y, double z) { Point ViewVector = new Point(ViewTo[0] - ViewFrom[0],
	 * ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]); Point DirectionVector =
	 * new Point(1, 1, 1); Point PlaneVector1 = VectorUtils.crossP(ViewVector,
	 * DirectionVector); Point PlaneVector2 = VectorUtils.crossP(ViewVector,
	 * PlaneVector1);
	 * 
	 * Point RotationVector = getRotationVector(ViewFrom, ViewTo); Point
	 * WeirdVector1 = VectorUtils.crossP(ViewVector, RotationVector); Point
	 * WeirdVector2 = VectorUtils.crossP(ViewVector, WeirdVector1);
	 * 
	 * Point ViewToPoint = new Point(x - ViewFrom[0], y - ViewFrom[1], z -
	 * ViewFrom[2]);
	 * 
	 * double t = (ViewVector.getX() * ViewTo[0] + ViewVector.getY() * ViewTo[1] +
	 * ViewVector.getZ() * ViewTo[2] - // (ViewVector.getX() * ViewFrom[0] +
	 * ViewVector.getY() * ViewFrom[1] + ViewVector.getZ() * ViewFrom[2])) / //
	 * (ViewVector.getX() * ViewToPoint.getX() + ViewVector.getY() *
	 * ViewToPoint.getY() + ViewVector.getZ() * ViewToPoint.getZ()); x = ViewFrom[0]
	 * + ViewToPoint.getX() * t; y = ViewFrom[1] + ViewToPoint.getY() * t; z =
	 * ViewFrom[2] + ViewToPoint.getZ() * t;
	 * 
	 * if (t >= 0) { DrawX = WeirdVector2.getX() * x + WeirdVector2.getY() * y +
	 * WeirdVector2.getZ() * z; DrawY = WeirdVector1.getY() * x +
	 * WeirdVector1.getY() * y + WeirdVector1.getZ() * z; } }
	 */

	public Point calcScreenPos(CSYS camera, Point point) {
		 double DrawX = Dist.dist(camera.getPlanes()[2], point); // distance of Point to yz plane
		 double DrawY = Dist.dist(camera.getPlanes()[1], point); // distance of Point to xz plane
		 DrawX*=screen.zoom;
		 DrawY*=screen.zoom;
		 DrawX+=screen.getPanelSize().getWidth()/2;
		 DrawY+=screen.getPanelSize().getHeight()/2;
		return new Point(DrawX, DrawY);
	}
	/*
	 * private static Point getRotationVector(double[] ViewFrom, double[] ViewTo) {
	 * double dx = Math.abs(ViewFrom[0] - ViewTo[0]); double dy =
	 * Math.abs(ViewFrom[1] - ViewTo[1]); double xRot, yRot; xRot = dy / (dx + dy);
	 * yRot = dx / (dx + dy); if (ViewFrom[1] > ViewTo[1]) xRot *= -1.; if
	 * (ViewFrom[0] < ViewTo[0]) yRot *= -1.;
	 * 
	 * Point V = new Point(xRot, yRot, 0); return V; }
	 */
}