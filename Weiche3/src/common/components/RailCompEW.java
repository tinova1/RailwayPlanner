package components;



public class RailCompEW {
/*
	private double a;
	private double len;
	private double gauge;
	private double flange;
	private double frog;
	// private double guide;

	private double r;

	private double a4; // needed in compute2()
	private Point p6;// needed incompute2()

	public RailCompEW(EW w) {

		a = w.getAngle();
		len = w.getLength();
		gauge = w.getGauge();
		flange = w.getRail().getFlangeFreedom();
		frog = w.getRail().getVFrogLength();
		// guide = w.getGuide_length();

		r = w.getRadius();
	}

	public ArrayList<RailDraw> computeOutline() {
		Rail r = new Rail();
		ArrayList<RailDraw> ausgabe = new ArrayList<>();
		double[] versatz = { 0., r.getRailHelper(), -r.getRailHead(), -r.getRailHead() - r.getRailHelper() };
		String[] position = { "head_inner", "foot_inner", "head_outer", "foot_outer" };
		for (int i = 0; i < versatz.length; i++) {
			ArrayList<RailDraw> computed = compute(versatz[i], position[i]);
			ausgabe.addAll(computed);
		}
		return ausgabe;
	}

	private ArrayList<RailDraw> compute(double versatz, String position) {
		// Versatz nach innen
		ArrayList<RailDraw> ausgabe = new ArrayList<>();

		double new_flange = flange - 2. * versatz;
		double new_gauge = gauge - 2. * versatz;
		double new_r_outer = r + new_gauge / 2.;
		double new_r_inner = r - new_gauge / 2.;

		// outer straight rail
		Point pm = new Point(0, -r, 0);
		Point p0r = new Point(0, -new_gauge / 2., 0);
		Point p0l = new Point(0, -new_gauge / 2., 0);
		Point p1l = new Point(len, new_gauge / 2., 0);
		Point p1r = new Point(len, -new_gauge / 2., 0);
		Point p0rf = new Point(0, -new_gauge / 2. + new_flange, 0);
		// double[] p0lf = { 0, new_gauge / 2. + new_flange };
		Point p1rf = new Point(len, -new_gauge / 2. + new_flange, 0);
		// double[] p1lf = { len, -new_gauge / 2. + new_flange };

		Circle outer_radius = new Circle(pm, new_r_outer);
		Circle outer_new_flange = new Circle(pm, new_r_outer - new_flange);

		Line inner_straight = new Line(p0r, p1r);
		Line inner_new_flange = new Line(p0rf, p1rf);

		// Herst�ckspitze
		Point p3 = inner_straight.intersection(outer_radius)[0];
		Point p4 = inner_new_flange.intersection(outer_radius)[0];
		Point p5 = inner_straight.intersection(outer_new_flange)[0];

		Point p6;
		if (versatz == 0.)
			p6 = new Point(p4.getX() + frog, -new_gauge / 2. + new_flange, 0.);
		else
			p6 = new Point(this.p6.getX(), -new_gauge / 2. + new_flange, 0.);

		double a1 = Math.asin(p3.getX() / new_r_outer);
		double a2 = Math.asin(p4.getX() / new_r_outer);
		double a3 = Math.asin(p5.getX() / (new_r_outer - new_flange));

		double a4;
		if (versatz == 0.)
			a4 = a3 + frog / (new_r_outer - new_flange);
		else
			a4 = this.a4;

		RailDraw l1l = new RailDraw(new LineSeg(p0l, p1l), "l1l", Side.LEFT, position, Side.LEFT);
		RailDraw l1r = new RailDraw(new LineSeg(p0r, p5), "l1r", Side.LEFT, position, Side.RIGHT);
		RailDraw l3r = new RailDraw(new LineSeg(p3, p1r), "l3r", Side.LEFT, position, Side.RIGHT);
		RailDraw l4l = new RailDraw(new LineSeg(p4, p6), "l4l", Side.LEFT, position, Side.LEFT);

		RailDraw l2l = new RailDraw(new Arc(pm, new_r_outer, Math.PI / 2. - a2, Math.PI / 2., RotDir.POS), "l2l",
				Side.RIGHT, position, Side.LEFT);
		RailDraw l2r = new RailDraw(new Arc(pm, new_r_inner, Math.PI / 2. - a, Math.PI / 2., RotDir.POS), "l2r",
				Side.RIGHT, position, Side.RIGHT);
		RailDraw l3l = new RailDraw(new Arc(pm, new_r_outer, Math.PI / 2. - a1, Math.PI / 2. - a, RotDir.POS), "l3l",
				Side.RIGHT, position, Side.LEFT);
		RailDraw l4r = new RailDraw(
				new Arc(pm, new_r_outer - new_flange, Math.PI / 2. - a3, Math.PI / 2. - a4, RotDir.POS), "l4r",
				Side.RIGHT, position, Side.RIGHT);

		ausgabe.add(l1l);
		ausgabe.add(l1r);
		ausgabe.add(l3r);
		ausgabe.add(l4l);
		ausgabe.add(l2l);
		ausgabe.add(l2r);
		ausgabe.add(l3l);
		ausgabe.add(l4r);

		this.p6 = p6;
		this.a4 = a4;
		return ausgabe;

	}*/
}
