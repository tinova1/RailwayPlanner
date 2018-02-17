package deprecated;

import common.components.Rail;
import common.railway.ew.PathTurnout;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Point;

public class ABWcreator {

	private final double r1, r2, a1, a2, gauge;
	private final Rail r;

	public ABWcreator(double gauge, Rail r, double r1, double r2, double a1, double a2) {
		this.r = r;
		this.r1 = r1;
		this.r2 = r2;
		this.a1 = a1;
		this.a2 = a2;
		this.gauge = gauge;
	}

	public PathTurnout pathTurnout() {
		return new PathTurnout(this.gauge, this.r, this.pathLeft(), this.pathRight());
	}

	private Path pathLeft() {
		final double st = -Math.PI / 2.;
		final double en = st + a1;
		final Arc arc = new Arc(new Point(0, r1), r1, st, en, RotDir.POS);
		return new Path(arc);
	}

	private Path pathRight() {
		final double st = Math.PI / 2.;
		final double en = st - a2;
		final Arc arc = new Arc(new Point(0, -r2), r2, st, en, RotDir.NEG);
		return new Path(arc);
	}
	/*
	 * private Path tiePath() { if (this.middleRadius < MathUtils.ROUND_ZERO) {
	 * final double length = Math.max(MathUtils.toLength(a1, r1),
	 * MathUtils.toLength(a2, r2)); final LineSeg seg = new LineSeg(new Point(0, 0),
	 * new Point(length, 0)); return new Path(seg); } else if (middleRadius > 0) {
	 * final double st = -Math.PI / 2.; final double en = Math.PI / 2.; final Arc
	 * arc = new Arc(new Point(0, middleRadius), middleRadius, st, en, RotDir.POS);
	 * return new Path(arc); } else {// middleRadius<0 final double st = Math.PI /
	 * 2.; final double en = -Math.PI / 2.; final Arc arc = new Arc(new Point(0,
	 * middleRadius), middleRadius, st, en, RotDir.NEG); return new Path(arc); } }
	 */
}
