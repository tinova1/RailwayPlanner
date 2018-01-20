package common.components;

import common.vectorMath.Dist;
import common.vectorMath.MathUtils;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.Point;

public abstract class TieBandComp {
	public static TieBand path(double start, double end, Path p, int startNo, String type, Tie t) {
		TieBand output = new TieBand();
		int runningNo = startNo;
		for (double length = start; length <= end; length += t.getDist(), runningNo++) {
			Tie newtie = new Tie(p.startPoint(), t.getLength(), runningNo, type);
			newtie.getCSYS().moveAlong(p, length, true);
			output.add(newtie);
		}
		return output;
	}

	public static TieBand pathWithBorder(final double start, final double end, final Path path, final Path border,
			final int startNo, final String type, final Tie t) {
		TieBand output = new TieBand();
		double s_laenge_neu = t.getLength();
		int runningNo = startNo;
		for (double length = start; s_laenge_neu <= t.getLongest()
				&& length <= path.getLength(); length += t.getDist(), runningNo++) {
			Tie newtie = new Tie(new Point(0, 0), t.getLength(), runningNo, type);
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
		for (double length = t.getDist()/2.; length < maxPathLength; length += t.getDist()) {
			final Point pLeft = pathLeft.pointAt(length);
			final Point pRight = pathRight.pointAt(length);
			if (Dist.dist2(pLeft, pRight) > MathUtils.ROUND_ZERO) {
				final Line line = new Line(pLeft, pRight);
				try {
					final Point pBorderLeft = borderLeft.intersection(line)[0];
					final Point pBorderRight = borderRight.intersection(line)[0];
					final Point origin = VectorUtils.middle(pBorderLeft, pBorderRight);
					final double tieLength = Dist.dist(pBorderLeft, pBorderRight);
					if(tieLength>t.getLongest()) 
						break;
					final Tie newTie = new Tie(origin, tieLength, 0, "");
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
				final Point startPoint = pathLeft.startPoint();
				output.add(new Tie(startPoint, t.getLength(), 0, ""));
			}
		}
		return output;
	}
	/*
	 * public static TieBand bw(BW bw, int startNo, String type) { if (bw instanceof
	 * IBW) { return ibw((IBW) bw, startNo, type); } else if (bw instanceof ABW) {
	 * return abw((ABW) bw, startNo, type); } else { return null; } }
	 * 
	 * private static TieBand ibw(IBW ibw, int startNo, String type) { final Tie t =
	 * ibw.getTie();
	 * 
	 * final double middleRadius; final double tieRadiusLeft; final Circle
	 * radiusLeft;
	 * 
	 * middleRadius = MathUtils.middleRadius(ibw.getR1(), ibw.getR2(),
	 * t.getLength()); tieRadiusLeft = ibw.getR1() + t.getLength() / 2.; radiusLeft
	 * = new Circle(new Point(0, -ibw.getR1(), 0), tieRadiusLeft);
	 * 
	 * TieBand output = new TieBand(); int runningNo = startNo; final double
	 * maxArcLength = Math.min(Math.abs(ibw.getR1() * ibw.getA1()),
	 * Math.abs(ibw.getR2() * ibw.getA2())); for (double arcLength = 0.; arcLength
	 * <= maxArcLength; arcLength += t.getDist(), runningNo++) { Tie newtie = new
	 * Tie(0., 0., 10., runningNo, type); newtie.getCube().getRef().rotate(new
	 * Point(0, -middleRadius, 0), -arcLength); double newTieLength =
	 * newTieLength(newtie, radiusLeft); if (newTieLength > t.getLongest()) { break;
	 * } newtie.setLength(newTieLength); newtie.setArcLength(arcLength);
	 * output.add(newtie); } return output; }
	 * 
	 * private static TieBand abw(ABW abw, int startNo, String type) { final Tie t =
	 * abw.getTie();
	 * 
	 * final double middleRadius; final double tieRadiusLeft; final Circle
	 * radiusLeft;
	 * 
	 * middleRadius = -MathUtils.middleRadius(abw.getR1(), -abw.getR2(),
	 * t.getLength()); tieRadiusLeft = abw.getR1() - t.getLength() / 2.; radiusLeft
	 * = new Circle(new Point(0, abw.getR1(), 0), tieRadiusLeft);
	 * 
	 * TieBand output = new TieBand(); int runningNo = startNo; final double
	 * maxArcLength = Math.min(Math.abs(abw.getR1() * abw.getA1()),
	 * Math.abs(abw.getR2() * abw.getA2())); for (double arcLength = 0.; arcLength
	 * <= maxArcLength; arcLength += t.getDist(), runningNo++) { Tie newtie = new
	 * Tie(0., 0., 10., runningNo, type); newtie.getCube().getRef().rotate(new
	 * Point(0, -middleRadius, 0), -arcLength); double newTieLength =
	 * newTieLength(newtie, radiusLeft); if (newTieLength > t.getLongest()) { break;
	 * } newtie.setLength(newTieLength); newtie.setArcLength(arcLength);
	 * output.add(newtie); } return output; }
	 */

	private static double newTieLength(Tie t, Curve c) {
		Point center = t.getCube().getCSYS().getPoint();
		Point connection = t.getLine().getDir();
		return 2. * Math.abs(Dist.projDist(center, connection, c));
	}
}
