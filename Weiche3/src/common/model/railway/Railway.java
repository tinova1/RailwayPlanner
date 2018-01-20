package common.model.railway;

import java.util.ArrayList;
import java.util.List;

import common.components.Kleineisen;
import common.components.Rail;
import common.components.RailDraw;
import common.components.Tie;
import common.components.TieBand;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Point;

public abstract class Railway {
	protected Rail r;
	protected Tie t;
	protected double gauge;
	private CSYS csys;

	protected Railway(CSYS position, Tie t, Rail r, double gauge) {
		this.r = r;
		this.t = t;
		this.gauge = gauge;
		this.csys = position;
	}

	protected Railway() {
		// default values
		this.r = new Rail();
		this.t = new Tie();
		this.gauge = 16.5;
		this.csys = new CSYS();
	}

	protected TieBand tieBand = new TieBand();
	protected List<RailDraw> railList = new ArrayList<>();
	protected List<Kleineisen> klein_list = new ArrayList<>();

	public TieBand getTieBand() {
		return tieBand;
	}

	public void setTieBand(TieBand input) {
		this.tieBand = input;
	}

	public List<RailDraw> getRailList() {
		return railList;
	}

	public void setRail_list(ArrayList<RailDraw> input) {
		railList = input;
	}

	public List<Kleineisen> getKlein_list() {
		return klein_list;
	}

	public void setKlein_list(ArrayList<Kleineisen> input) {
		klein_list = input;
	}

	public CSYS getCSYS() {
		return this.csys;
	}

	public void move(final Point dp) {
		this.csys.move(dp);
		this.tieBand.move(dp);
		for (RailDraw i : railList) {
			i.getCurve().move(dp);
		}
	}

	public void rotate(final double drot) {
		this.rotate(this.csys.getPoint(), drot);
	}

	public void rotate(final Point center, final double drot) {
		this.csys.rotate(center, drot);
		this.tieBand.rotate(center, drot);
		for (RailDraw i : railList) {
			i.getCurve().rotate(center, drot);
		}
	}

	public Tie getTie() {
		return this.t;
	}

	public Rail getRail() {
		return this.r;
	}

	public double getGauge() {
		return this.gauge;
	}

}
