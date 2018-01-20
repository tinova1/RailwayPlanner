package model.railway;

import components.Rail;
import components.RailComp;
import components.Side;
import components.Tie;
import components.TieBandComp;
import vectorMath.objects2D.Path;
import vectorMath.objects3D.CSYS;

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
