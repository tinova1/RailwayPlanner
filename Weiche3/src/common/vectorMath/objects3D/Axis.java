package common.vectorMath.objects3D;

public enum Axis {
	X(new Point(1, 0, 0), 0), //
	Y(new Point(0, 1, 0), 1), //
	Z(new Point(0, 0, 1), 2); //

	private final Point p;
	private final int no;

	Axis(Point p, final int no) {
		this.p = p;
		this.no = no;
	}

	public Point getPoint() {
		return this.p;
	}

	public Line getLine() {
		return new Line(new Point(0, 0, 0), p);
	}

	public int getNo() {
		return this.no;
	}
}
