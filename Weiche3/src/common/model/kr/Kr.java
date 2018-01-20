package common.model.kr;

import java.util.ArrayList;

import common.components.Rail;
import common.components.RailCompKr;
import common.components.Tie;
import common.components.TieBand;
import common.components.TieBandComp;
import common.model.railway.Railway;
import common.vectorMath.Orientation;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Axis;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Kr extends Railway {
	private final double l1, l2, l3, l4, angle;
	
	private final RailCompKr railCompKr = new RailCompKr(this);

	public Kr(final double gauge, final Rail r, final double l1, final double l2, final double l3, final double l4,
			final double angle) {
		super(new CSYS(), new Tie(), r, gauge);
		this.l1 = l1;
		this.l2 = l2;
		this.l3 = l3;
		this.l4 = l4;
		this.angle = angle;

		this.calcTies();
		
		this.railCompKr.calcRails();
	}

	public Path path(final int from, final int to) {
		final LineSeg seg = new LineSeg(p()[from], p()[to]);
		return new Path(seg);
	}

	private Point[] p() {
		Point[] points = new Point[5];
		points[0] = new Point(0, 0);
		points[1] = new Point(l1, 0);
		points[1].rotate(angle);
		points[2] = new Point(-l2, 0);
		points[3] = new Point(-l3, 0);
		points[3].rotate(angle);
		points[4] = new Point(l4, 0);
		return points;
	}

	private Path getTiePathRight() {
		final double lRight = minLengthRight();
		final Point p1 = new Point(0, 0);
		final Point p2 = new Point(lRight, 0);
		final LineSeg seg = new LineSeg(p1, p2);
		seg.rotate(new Point(0, 0), angle / 2.);
		return new Path(seg);
	}

	private Path getTiePathLeft() {
		final double lLeft = minLengthLeft();
		final Point p1 = new Point(0, 0);
		final Point p2 = new Point(-lLeft, 0);
		final LineSeg seg = new LineSeg(p1, p2);
		seg.rotate(new Point(0, 0), angle / 2.);
		return new Path(seg);
	}

	private void calcTies() {
		final Tie tie = this.getTie();
		final TieBand tbLeft = new TieBand();
		final TieBand tbRight = new TieBand();
		// right
		Path tieBorder = path(0, 1).offsetClone(-tie.getLength() / 2.);
		tbRight.add(TieBandComp.pathWithBorder(0, minLengthRight(), getTiePathRight(), tieBorder, 0, "Kr", tie));
		// left
		tieBorder = path(0, 2).offsetClone(tie.getLength() / 2.);
		tbLeft.add(TieBandComp.pathWithBorder(tie.getDist(), minLengthLeft(), getTiePathLeft(), tieBorder,
				tbLeft.getTieList().size(), "Kr", tie));
		final ArrayList<Tie> tieListRight = tbRight.getTieList();
		final ArrayList<Tie> tieListLeft = tbLeft.getTieList();
		final Tie lastTieRight = tieListRight.get(tieListRight.size() - 1);
		final Tie lastTieLeft = tieListLeft.get(tieListLeft.size() - 1);

		final double maxRight = lastTieRight.getCube().outermost(Axis.X, Orientation.GLOBAL).getX() + tie.getDist();
		final double maxLeft = lastTieLeft.getCube().innermost(Axis.X, Orientation.GLOBAL).getX() - tie.getDist();

		final TieBand tb1 = TieBandComp.path(maxRight, l1, path(0, 1), 0, "L1", tie);
		final TieBand tb2 = TieBandComp.path(-maxLeft, l2, path(0, 2), 0, "L2", tie);
		final TieBand tb3 = TieBandComp.path(-maxLeft, l3, path(0, 3), 0, "L3", tie);
		final TieBand tb4 = TieBandComp.path(maxRight, l4, path(0, 4), 0, "L4", tie);

		this.tieBand.add(tbRight);
		this.tieBand.add(tbLeft);
		this.tieBand.add(tb1);
		this.tieBand.add(tb2);
		this.tieBand.add(tb3);
		this.tieBand.add(tb4);
	}

	private double minLengthLeft() {
		return Math.min(l2, l3);
	}

	private double minLengthRight() {
		return Math.min(l1, l4);
	}

	
}
