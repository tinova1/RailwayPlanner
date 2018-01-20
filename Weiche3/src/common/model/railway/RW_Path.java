package common.model.railway;

import common.components.Rail;
import common.components.RailComp;
import common.components.Side;
import common.components.Tie;
import common.components.TieBandComp;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.CSYS;

public class RW_Path extends Railway {
	private Path path;

	public RW_Path(double gauge, Rail rail, final Path path) {
		super(new CSYS(), new Tie(), rail, gauge);
		this.path = path;
		this.calcTies();
		this.calcRail();
	}

	public Path getPath() {
		return this.path;
	}

	private void calcTies() {
		this.setTieBand(TieBandComp.path(0, path.getLength(), path, 0, "", this.t));
	}

	private void calcRail() {
		this.railList.addAll(RailComp.path(path, this.gauge, this.r, Side.BOTH));
	}
}
