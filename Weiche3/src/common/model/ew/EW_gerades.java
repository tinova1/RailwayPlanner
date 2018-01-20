package common.model.ew;

import common.components.Rail;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class EW_gerades {
	private final double gauge;
	private final Rail r;
	private final double length;
	private final double radius;
	private final double angle;
	private final double verlaengerung;

	public EW_gerades(double gauge, Rail r, double length, double radius, double angle, double verlaengerung) {
		this.gauge = gauge;
		this.r = r;
		this.length = length;
		this.radius = radius;
		this.angle = angle;
		this.verlaengerung = verlaengerung;
	}

	public PathTurnout pathTurnout() {
		return new PathTurnout(gauge, r, this.pathLeft(), this.pathRight(), this.tiePath());
	}

	public Path pathLeft() {
		final LineSeg l = new LineSeg(new Point(0, 0), new Point(this.length, 0));
		final Path p = new Path();
		p.add(l);
		return p;
	}

	public Path pathRight() {
		final Point center = new Point(0, -this.radius);
		final double start = Math.PI / 2.;
		final double end = start - this.angle;
		final Arc arc = new Arc(center, this.radius, start, end, RotDir.NEG);
		Path output = new Path();
		output.add(arc);
		output.addLineSeg(this.verlaengerung);
		/*final Point startLine = arc.getEndPoint();
		final LineSeg line = new LineSeg(startLine, -this.angle, this.verlaengerung);
		output.add(arc);
		output.add(line);*/
		return output;
	}

	private Path tiePath() {
		final Point center = new Point(0, -this.radius * 2.);
		final double start = Math.PI / 2.;
		final double end = start - this.angle / 2.;
		final Arc arc = new Arc(center, this.radius * 2., start, end, RotDir.NEG);
		final LineSeg lineSeg = new LineSeg(arc.getEndPoint(), -this.angle / 2., this.length);
		final Path path = new Path();
		path.add(arc);
		path.add(lineSeg);
		return path;
	}
}
