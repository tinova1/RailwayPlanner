package components;

public class Rail {

	private double railFoot = .5;
	private double railHead = .3;

	private double vFrogLength = 10.; // Length of "Frog" rails
	private double kFrogLength = 8.;
	private double guideLength = 20.; // Length of Guide rails
	private double flangeFreedom = 1.;

	public Rail(double foot, double head, double vFrog, final double kFrog, double guide, double flange) {
		this.railFoot = foot;
		this.railHead = head;
		this.vFrogLength = vFrog;
		this.kFrogLength = kFrog;
		this.guideLength = guide;
		this.flangeFreedom = flange;
	}

	public Rail() {
		// use default values
	}

	public double getRailFoot() {
		return railFoot;
	}

	public double getRailHead() {
		return railHead;
	}

	public double getRailHelper() {
		return (railFoot - railHead) / 2.;
	}

	public double getVFrogLength() {
		return this.vFrogLength;
	}

	public double getKFrogLength() {
		return this.kFrogLength;
	}

	public double getGuideLength() {
		return this.guideLength;
	}

	public double getFlangeFreedom() {
		return this.flangeFreedom;
	}

	public double[] getOffsets() {
		return new double[] { 0., this.getRailHelper(), -this.getRailHead(),
				-this.getRailHead() - this.getRailHelper() };
	}

	public double getOffset(final Positions pos) {
		return this.getOffsets()[pos.getIndex()];
	}

	public String[] getPositions() {
		return new String[] { "head_inner", "foot_inner", "head_outer", "foot_outer" };
	}

}
