package dddEngine;

import java.awt.Color;

import common.vectorMath.Dist;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects3D.Point;

public class DDDPolygon {
	Color c;
	Point[] verts; // 3d Points

	public DDDPolygon(Point[] verts) {
		this.verts = verts;
		this.c = Color.pink;
	}

	public DDDPolygon(Point[] verts, Color c) {
		this.verts = verts;
		this.c = c;
	}

	double GetDist() {
		/*
		 * double total = 0; for (int i = 0; i < this.verts.length; i++) { total +=
		 * Dist.dist(this.verts[i], Screen.camera.getPoint()); } return total / (double)
		 * this.verts.length;
		 */

		// return the minimum length to camera's xy plane
		double returnVal = Double.MAX_VALUE;
		for (Point p : this.verts) {
			final double distance = Dist.projDist(p, Screen.ViewFrom,
					VectorUtils.subtract(Screen.ViewFrom, Screen.ViewTo));
			returnVal = Math.min(returnVal, distance);
		}
		return returnVal;
	}

	public void setColor(final Color c) {
		this.c = c;
	}
}