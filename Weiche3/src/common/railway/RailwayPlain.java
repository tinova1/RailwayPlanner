package common.railway.plain;

import common.components.Rail;
import common.components.Tie;
import common.computers.rail.RailCompPlain;
import common.computers.tie.TieBandComp;
import common.railway.Railway;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.CSYS;
import utils.Side;

public class RailwayPlain extends Railway {
	private Path path;

	public RailwayPlain(double gauge, Rail rail, final Path path) {
		super(new CSYS(), new Tie(), rail, gauge);
		this.path = path;
		this.calcTies();
		this.calcRail();
	}

	public Path getPath() {
		return this.path;
	}

	private void calcTies() {
		this.setTieBand(TieBandComp.path(this.t.getDist()/2., path.getLength(), path, 0, "", this.t));
	}

	private void calcRail() {
		this.railList.addAll(RailCompPlain.path(path, this.gauge, this.r, Side.BOTH));
	}
}
