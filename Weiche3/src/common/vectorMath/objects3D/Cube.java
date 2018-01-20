package common.vectorMath.objects3D;

import java.util.ArrayList;

import common.svgCreator.Tag;
import common.vectorMath.Orientation;
import common.vectorMath.Polyhedron;
import common.vectorMath.objects2D.Path;

public class Cube extends Polyhedron {

	/**
	 * ref is center of cube method 0: c is center of cube method 1: c is on lower
	 * left corner of cube method 2: c is center of upper plane method 3: c is
	 * center of front lower line
	 */
	public Cube(CSYS c, int method) {
		super();
		this.csys = c;

		this.verts.add(0, new Point(-.5, .5, .5));
		this.verts.add(1, new Point(.5, .5, .5));
		this.verts.add(2, new Point(-.5, -.5, .5));
		this.verts.add(3, new Point(.5, -.5, .5));
		this.verts.add(4, new Point(-.5, .5, -.5));
		this.verts.add(5, new Point(.5, .5, -.5));
		this.verts.add(6, new Point(-.5, -.5, -.5));
		this.verts.add(7, new Point(.5, -.5, -.5));

		this.faces.add(new int[] { 1, 2, 4, 3 });
		this.faces.add(new int[] { 8, 7, 5, 6 });
		this.faces.add(new int[] { 1, 2, 6, 5 });
		this.faces.add(new int[] { 2, 4, 8, 6 });
		this.faces.add(new int[] { 4, 3, 7, 8 });
		this.faces.add(new int[] { 1, 5, 7, 3 });

		switch (method) {
		case 1:
			this.getCSYS().move(-.5, -.5, -.5);
			break;
		case 2:
			this.getCSYS().move(0., 0., .5);
			break;
		case 3:
			this.getCSYS().move(0., .5, -.5);
			break;
		}

	}

	public Point getDimensions() {
		return new Point(this.csys.getScale());
	}

	public double getDiagonal() {
		return Math.sqrt(this.getDiagonal2());
	}

	public double getDiagonal2() {
		return this.getDimensions().getLength2();
	}

	public LineSeg[] project(Axis axis, Orientation o) {
		LineSeg[] rect = new LineSeg[4];
		final ArrayList<Point> verts = this.getVerts(o);

		rect[0] = new LineSeg(verts.get(0).project(axis), verts.get(1).project(axis));
		rect[1] = new LineSeg(verts.get(1).project(axis), verts.get(3).project(axis));
		rect[2] = new LineSeg(verts.get(3).project(axis), verts.get(2).project(axis));
		rect[3] = new LineSeg(verts.get(2).project(axis), verts.get(0).project(axis));
		return rect;
	}

	public Cube clone() {
		return new Cube(this.getCSYS(), 0);
	}

	public Path getPath() {
		Path path = new Path();
		final ArrayList<Point> vertsGl = this.getVerts(Orientation.GLOBAL);
		path.add(new LineSeg(vertsGl.get(0), vertsGl.get(1)));
		path.add(new LineSeg(vertsGl.get(1), vertsGl.get(3)));
		path.add(new LineSeg(vertsGl.get(3), vertsGl.get(2)));
		path.add(new LineSeg(vertsGl.get(2), vertsGl.get(0)));
		return path;
	}

	public Tag export_svg() {
		return this.getPath().export_svg();
	}
}