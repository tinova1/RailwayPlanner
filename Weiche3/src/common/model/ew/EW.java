package common.model.ew;

import common.model.railway.Railway;


public class EW extends Railway {
	/*private double length;
	private double radius;
	private double angle;

	public EW(double gauge, Rail r, double length, double radius, double angle) {

		super(new CSYS(), new Tie(), r, gauge);
		this.length = length;
		this.radius = radius;
		this.angle = Math.toRadians(angle);

		this.calcTies();
		this.calcRail();
	}

	public double getLength() {
		return length;
	}

	public double getAngle() {
		return angle;
	}

	public double getRadius() {
		return radius;
	}

	private void calcTies() {
		Tie t = this.getTie();
		TieBand output = new TieBand();

		output.add(TieBandComp.ew(this, 0, "turnout"));

		// gerades Schwellenband nach Weichenschwellenband
		ArrayList<Tie> tieList = output.getTieList();
		Tie lastTie = tieList.get(tieList.size() - 1);
		double beginn_gerade = lastTie.getCube().max(Orientation.GLOBAL)[0] + lastTie.getWidth() / 2.
				+ t.getDist() / 2.;
		output.add(TieBandComp.straight(beginn_gerade, this.getLength(), tieList.size(), "straight", this.getTie()));

		// gebogenes Schwellenband nach Weichenschwellenband
		double beginn_kurve = beginn_gerade / this.getRadius();
		output.add(TieBandComp.curve(beginn_kurve, this.getAngle(), this.getRadius(), tieList.size(), "curve",
				this.getTie()));

		this.tieBand = output;
	}

	private void calcRail() {
		RailCompEW r = new RailCompEW(this);
		this.setRail_list(r.computeOutline());
	}

	// private void calcKlein() {
	// KleineisenCalculator k = new KleineisenCalculator(this);
	// k.berechnen2();
	// }*/
}
