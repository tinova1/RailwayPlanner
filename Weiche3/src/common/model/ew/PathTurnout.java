package common.model.ew;

import java.util.ArrayList;

import common.components.Rail;
import common.components.RailCompEW_gerades;
import common.components.Tie;
import common.components.TieBand;
import common.components.TieBandComp;
import common.model.railway.Railway;
import common.vectorMath.Orientation;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.CSYS;

public class PathTurnout extends Railway {
	private final Path pathLeft, pathRight, tiePath;

	public PathTurnout(double gauge, Rail r, final Path pathLeft, final Path pathRight, final Path tiePath) {

		super(new CSYS(), new Tie(), r, gauge);
		this.pathLeft = pathLeft;
		this.pathRight = pathRight;
		this.tiePath = tiePath;

		this.calcTies();
		this.calcRail();
	}

	private void calcTies() {
		final Tie tie = this.getTie();
		final TieBand output = new TieBand();
		Path tieBorder = this.getPathRight().clone();
		tieBorder.offset(tie.getLength() / 2.);
		// output.add(TieBandComp.pathWithBorder(0, pathLeft.getLength(), this.tiePath,
		// tieBorder, 0, "turnout", tie));
		output.add(TieBandComp.twoPaths(this.getPathLeft(), this.getPathRight(), tie));

		// linkes Schwellenband
		ArrayList<Tie> tieList = output.getTieList();
		Tie lastTie = tieList.get(tieList.size() - 1);
		double beginn_gerade = lastTie.getCube().max(Orientation.GLOBAL)[0] + lastTie.getWidth() / 2.
				+ tie.getDist() / 2.;
		output.add(TieBandComp.path(beginn_gerade, pathLeft.getLength(), this.pathLeft, tieList.size(), "straight",
				this.getTie()));

		// rechtes Schwellenband
		output.add(TieBandComp.path(beginn_gerade, this.pathRight.getLength(), this.pathRight, tieList.size(), "curve",
				this.getTie()));

		this.tieBand = output;
	}

	private void calcRail() {
		RailCompEW_gerades r = new RailCompEW_gerades(this);
		this.railList.addAll(r.computeOutline());
	}

	public Path getPathLeft() {
		return this.pathLeft;
	}

	public Path getPathRight() {
		return this.pathRight;
	}

	public Path tiepath() {
		return this.tiePath;
	}

	// private void calcKlein() {
	// KleineisenCalculator k = new KleineisenCalculator(this);
	// k.berechnen2();
	// }
}