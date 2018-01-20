package common.guiUtils;

public enum Gauges {
	II(22.5, 64), //
	IIm(22.5, 45), //
	IIe(22.5, 32), //
	IIi(22.5, 22.5), //
	IIp(22.5, 16.5), //
	I(32, 45), //
	Im(32, 32), //
	Ie(32, 23.4), //
	Ii(32, 16.5), //
	Ip(32, 12), //
	O(45, 32), //
	Om(45, 22.5), //
	Oe(45, 16.5), //
	Oi(45, 12), //
	Op(45, 9), //
	S(64, 22.5), //
	Sm(64, 16.5), //
	Se(64, 12), //
	Si(64, 9), //
	Sp(64, 6.5), //
	H0(87, 16.5), //
	H0m(87, 12), //
	H0e(87, 9), //
	H0i(87, 6.5), //
	H0f(87, 6.5), //
	H0p(87, 4.5), //
	TT(120, 12), //
	TTm(120, 9), //
	TTe(120, 6.5), //
	TTi(120, 4.5), //
	N(160, 9), //
	Nm(160, 6.5), //
	Ne(160, 4.5), //
	Z(220, 6.5), //
	Zm(220, 4.5), //
	Custom(87, 16.5);//

	private double scale;
	private double gauge;

	Gauges(double scale, double gauge) {
		this.scale = scale;
		this.gauge = gauge;
	}

	public double getScale() {
		return this.scale;
	}

	public double getGauge() {
		return this.gauge;
	}
}
