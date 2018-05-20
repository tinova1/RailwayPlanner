package common.railway.kr;

import java.util.ArrayList;

import common.components.Rail;
import common.components.Tie;
import common.computers.kleineisen.KleinCompPathTurnout;
import common.computers.rail.RailCompPathKr;
import common.computers.tie.TieBand;
import common.computers.tie.TieBandComp;
import common.railway.Railway;
import common.vectorMath.Dist;
import common.vectorMath.MathUtils;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Point;

public class PathKr extends Railway {

	// * Schemabild der Kreuzung
	// *              .-' 1
	// *           .-'
	// * 2 ------0------- 4
	// *     .-'
	// * 3 -'

	private final Path[] path = new Path[4]; // {path13, path31, path24, path42}
	private final Path path01;
	private final Path path02;
	private final Path path03;
	private final Path path04;

	private final RailCompPathKr railCompKr = new RailCompPathKr(this);

	public PathKr(final double gauge, final Rail r, final Path path13, final Path path24) {
		super(new CSYS(), new Tie(), r, gauge);
		intersControl(path13, path24);
		this.path[0] = path13.clone();
		path13.reverse();
		this.path[1] = path13;
		this.path[2] = path24.clone();
		path24.reverse();
		this.path[3] = path24;

		this.path01 = this.path(1);
		this.path02 = this.path(2);
		this.path03 = this.path(3);
		this.path04 = this.path(4);

		this.calcTies();
		this.railCompKr.calcRails();
		this.calcKlein();
	}

	private static void intersControl(final Path path1, final Path path2) {
		/*
		 * Exit, if paths have 0 or more than one intersection
		 */
		final Point[] intersection = path1.intersection(path2);
		if (intersection.length == 1)
			return;
		else if (intersection.length < 1)
			System.out.println("Pfade schneiden sich nicht!");
		else // intersection.length > 1
			System.out.println("Pfade schneiden sich mehrmals!");
		System.exit(-1);

	}

	public Path[] getPath() {
		return this.path;
	}

	private Point crossing() {
		/*
		 * Gibt den Schnittpunkt beide Kreuzungspfade zurück Wenn sich die Pfade nicht
		 * oder mehrmals schneiden, wird null zurückgegeben
		 */
		final Point[] intersection = path[0].intersection(path[2]);
		if (intersection.length == 1)
			return intersection[0];
		else
			return null;
	}

	private Path path(/* int from=0 */ final int to) {
		// from intersection to point
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
		final TieBand tb1 = TieBandComp.path(maxRight, l1, path01, 0, tie);
		final TieBand tb2 = TieBandComp.path(maxLeft, l2, path02, 0, tie);
		final TieBand tb3 = TieBandComp.path(maxLeft, l3, path03, 0, tie);
		final TieBand tb4 = TieBandComp.path(maxRight, l4, path04, 0, tie);

		this.tieBand.add(tbRight);
		this.tieBand.add(tbLeft);
		this.tieBand.add(tb1);
		this.tieBand.add(tb2);
		this.tieBand.add(tb3);
		this.tieBand.add(tb4);
	}

	private void calcKlein() {
		this.kleinList = KleinCompPathTurnout.compute(this, true);
	}

	private double startValue(final Tie t) {
		final double hypotenuse2 = Dist.dist2(this.crossing(), t.endPoints()[0]);
		return Math.sqrt((hypotenuse2) - MathUtils.sq(this.t.getLength() / 2.));
	}
}
