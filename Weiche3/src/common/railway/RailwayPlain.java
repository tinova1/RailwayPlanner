package common.railway;

import common.components.Rail;
import common.components.Tie;
import common.computers.kleineisen.KleinCompPathTurnout;
import common.computers.rail.RailCompPlain;
import common.computers.tie.TieBandComp;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.CSYS;
import utils.Side;

public class RailwayPlain extends Railway {

	// a railway that goes along a path, nothing special

	private Path path;

	public RailwayPlain(double gauge, Rail rail, final Path path) {
		super(new CSYS(), new Tie(), rail, gauge);
		this.path = path;
		this.calcTies();
		this.calcRail();
		this.calcKlein();
	}

	public Path getPath() {
		return this.path;
	}

	private void calcTies() {
		this.setTieBand(TieBandComp.path(this.t.getDist() / 2., path.getLength(), path, 0, this.t));
	}

	private void calcRail() {
		this.railList.addAll(RailCompPlain.path(path, this.gauge, this.r, Side.BOTH));
	}

	private void calcKlein() {
		this.kleinList = KleinCompPathTurnout.compute(this, false);
	}
}
