package common.vectorMath.objects2D;

import common.svgCreator.Tag;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public abstract class Curve {

	public abstract Tag export_svg();

	public abstract void move(final Point dp);

	public abstract Curve clone();

	public abstract void rotate(final Point center, final double drot);

	public abstract void trim(Curve c, boolean reverse);

	public Curve trimClone(Curve c, boolean reverse) {
		final Curve clone = this.clone();
		clone.trim(c, reverse);
		return clone;
	}

	public abstract String print();

	public abstract double getLength();

	public abstract Curve getReverse();

	public Point[] intersection(Curve c) {
		if (this instanceof Path) {
			return ((Path) this).intersection(c);
		} else if (this instanceof LineSeg) {
			return ((LineSeg) this).intersection(c);
		} else if (this instanceof Line) {
			return ((Line) this).intersection(c);
		} else if (this instanceof Arc) {
			return ((Arc) this).intersection(c);
		} else if (this instanceof Circle) {
			return ((Circle) this).intersection(c);
		} else {
			System.out.println("Unbekannter Curve-Typ");
			return null;
		}
	}

	public abstract Point getStartPoint();

	public abstract Point getEndPoint();

	public abstract void setStartPoint(final Point p);

	public abstract void setEndPoint(final Point p);


}
