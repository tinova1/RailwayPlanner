package model.kr;

import java.util.ArrayList;

import components.Rail;
import components.Tie;
import components.TieBand;
import components.TieBandComp;
import model.railway.Railway;
import vectorMath.Dist;
import vectorMath.MathUtils;
import vectorMath.objects2D.Path;
import vectorMath.objects3D.CSYS;
import vectorMath.objects3D.Point;

public class PathKr extends Railway {

	private final Path[] path = new Path[4];
	private final Path path01;
	private final Path path02;
	private final Path path03;
	private final Path path04;

	private final RailCompPathKr railCompKr = new RailCompPathKr(this);

	public PathKr(final double gauge, final Rail r, final Path path13, final Path path24) {
		super(new CSYS(), new Tie(), r, gauge);
		this.path[0] = path13.clone();
		path13.reverse();
		this.path[1] = path13;
		this.path[2] = path24.clone();
		path24.reverse();
		this.path[3] = path24;
		if (this.crossing() == null) {
			System.out.println("Pfade schneiden sich nicht oder mehrfach!");
		}
		this.path01 = this.path(1);
		this.path02 = this.path(2);
		this.path03 = this.path(3);
		this.path04 = this.path(4);

		this.calcTies();

		this.railCompKr.calcRails();
	}

	public Path[] getPath() {
		return this.path;
	}

	private Point crossing() {
		final Point[] intersection = path[0].intersection(path[2]);
		if (intersection.length == 1)
			return path[0].intersection(path[2])[0];
		else
			return null;
	}

	private Path path(final int to) {
		// from intersection to point
		final Point[] inters = path[0].intersection(path[2]);
		if (inters.length == 0) {
			System.out.println("Pfade schneiden sich nicht!");
		}
		final Path path13 = this.path[0].clone();
		final Path path24 = this.path[2].clone();
		switch (to) {
		case 1:
			path13.trim(path24, false);
			path13.reverse();
			return path13;
		case 2:
			path24.trim(path13, false);
			path24.reverse();
			return path24;
		case 3:
			path13.trim(path24, true);
			return path13;
		case 4:
			path24.trim(path13, true);
			return path24;
		default:
			return null;
		}
	}

	/*
	 * private Path borderPath(final boolean upper) { final Path path13 =
	 * this.path[0].clone(); final Path path24 = this.path[2].clone();
	 * path24.reverse(); final Path output = new Path(); if (upper) {
	 * path13.offset(this.gauge / 2.); path24.offset(-this.gauge / 2.);
	 * path13.trimTwo(path24, false, true); output.add(path13); output.add(path24);
	 * } else { path13.offset(-this.gauge / 2.); path24.offset(this.gauge / 2.);
	 * path13.trimTwo(path24, true, false); output.add(path24); output.add(path13);
	 * } return output; }
	 */

	private void calcTies() {
		final Tie tie = this.getTie();
		final TieBand tbLeft = new TieBand();
		final TieBand tbRight = new TieBand();
		// right
		tbRight.add(TieBandComp.twoPaths(this.path(1), this.path(4), this.t));
		// left
		tbLeft.add(TieBandComp.twoPaths(this.path(3), this.path(2), this.t));

		final ArrayList<Tie> tieListRight = tbRight.getTieList();
		final ArrayList<Tie> tieListLeft = tbLeft.getTieList();
		final Tie lastTieRight = tieListRight.get(tieListRight.size() - 1);
		final Tie lastTieLeft = tieListLeft.get(tieListLeft.size() - 1);

		final double maxRight = this.startValue(lastTieRight) + this.t.getWidth() * 1.5;
		final double maxLeft = this.startValue(lastTieLeft) + this.t.getWidth() * 1.5;

		final double l1 = this.path01.getLength();
		final double l2 = this.path02.getLength();
		final double l3 = this.path03.getLength();
		final double l4 = this.path04.getLength();
		final TieBand tb1 = TieBandComp.path(maxRight, l1, path01, 0, "L1", tie);
		final TieBand tb2 = TieBandComp.path(maxLeft, l2, path02, 0, "L2", tie);
		final TieBand tb3 = TieBandComp.path(maxLeft, l3, path03, 0, "L3", tie);
		final TieBand tb4 = TieBandComp.path(maxRight, l4, path04, 0, "L4", tie);

		this.tieBand.add(tbRight);
		this.tieBand.add(tbLeft);
		this.tieBand.add(tb1);
		this.tieBand.add(tb2);
		this.tieBand.add(tb3);
		this.tieBand.add(tb4);
	}

	private double startValue(final Tie t) {
		final double hypotenuse2 = Dist.dist2(this.crossing(), t.endPoints()[0]);
		return Math.sqrt((hypotenuse2) - MathUtils.sq(this.t.getLength() / 2.));
	}
}
