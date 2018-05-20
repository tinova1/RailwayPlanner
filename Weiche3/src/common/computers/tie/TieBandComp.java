package common.computers.tie;

import common.components.Tie;
import common.vectorMath.Dist;
import common.vectorMath.MathUtils;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.Point;

public abstract class TieBandComp {
	public static TieBand path(double start, double end, Path p, int startNo, Tie t) {
		TieBand output = new TieBand();
		int runningNo = startNo;
		for (double length = start; length <= end; length += t.getDist(), runningNo++) {
			Tie newtie = new Tie(p.getStartPoint().clone(), t.getLength(), runningNo);
			newtie.getCSYS().moveAlong(p, length, true);
			output.add(newtie);
		}
		return output;
	}

	public static TieBand pathWithBorder(final double start, final double end, final Path path, final Path border,
			final int startNo, final Tie t) {
		TieBand output = new TieBand();
		double s_laenge_neu = t.getLength();
		int runningNo = startNo;
		for (double length = start; s_laenge_neu <= t.getLongest()
				&& length <= path.getLength(); length += t.getDist(), runningNo++) {
			Tie newtie = new Tie(new Point(0, 0), t.getLength(), runningNo);
			newtie.getCSYS().moveAlong(path, length, true);
			double newTieLength = newTieLength(newtie, border);
			if (Double.isInfinite(newTieLength)) {
				newTieLength = t.getLength();
			} else if (newTieLength > t.getLongest()) {
				break;
			}
			newtie.setLength(newTieLength);
			output.add(newtie);
		}
		return output;
	}

	public static TieBand twoPaths(final Path pathLeft, final Path pathRight, final Tie t) {
		TieBand output = new TieBand();
		final double maxPathLength = Math.min(pathLeft.getLength(), pathRight.getLength());
		final Path borderLeft = pathLeft.offsetClone(-t.getLength() / 2.);
		final Path borderRight = pathRight.offsetClone(t.getLength() / 2.);
		for (double length = t.getDist() / 2.; length < maxPathLength; length += t.getDist()) {
			final Point pLeft = pathLeft.pointAt(length);
			final Point pRight = pathRight.pointAt(length);
			if (Dist.dist2(pLeft, pRight) > MathUtils.ROUND_ZERO) {
				final Line line = new Line(pLeft, pRight);
				try {
					final Point pBorderLeft = borderLeft.intersection(line)[0];
					final Point pBorderRight = borderRight.intersection(line)[0];
					final Point origin = VectorUtils.middle(pBorderLeft, pBorderRight);
					final double tieLength = Dist.dist(pBorderLeft, pBorderRight);
					if (tieLength > t.getLongest())
						break;
					final Tie newTie = new Tie(origin, tieLength, 0);
					double angle = line.getAngle();
					if (angle > 0) {
						angle -= Math.PI / 2.;
					} else {
						angle += Math.PI / 2.;
					}
					newTie.rotate(angle);
					output.add(newTie);
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			} else {
				final Point startPoint = pathLeft.getStartPoint();
				output.add(new Tie(startPoint, t.getLength(), 0));
			}
		}
		return output;
	}

	private static double newTieLength(final Tie t, final Curve c) {
		final Point center = t.getCube().getCSYS().getPoint();
		final Point connection = t.getLineSeg().getDir();
		return 2. * Math.abs(Dist.projDist(center, connection, c));
	}
}
