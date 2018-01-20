package common.components;

public class RailCompABW {
	/*
	 * private final ABW abw; private final Rail rail; private final double r1;
	 * private final double r2; private final double a1; private final double a2;
	 * 
	 * private double aL; private double aR;
	 * 
	 * public RailCompABW(ABW abw) { this.abw = abw; this.rail = abw.getRail();
	 * this.r1 = abw.getR1(); this.r2 = abw.getR2(); this.a1 = abw.getA1(); this.a2
	 * = abw.getA2(); }
	 * 
	 * public ArrayList<RailDraw> computeOutline() { ArrayList<RailDraw> ausgabe =
	 * new ArrayList<>(); double[] versatz = { 0., this.rail.getRailHelper(),
	 * -this.rail.getRailHead(), -this.rail.getRailHead() -
	 * this.rail.getRailHelper() }; String[] position = { "head_inner",
	 * "foot_inner", "head_outer", "foot_outer" }; for (int i = 0; i <
	 * versatz.length; i++) { ArrayList<RailDraw> computed = compute(versatz[i],
	 * position[i]); ausgabe.addAll(computed); } return ausgabe; }
	 * 
	 * private ArrayList<RailDraw> compute(double versatz, String position) {
	 * 
	 * final double new_flange = this.rail.getFlangeFreedom() - 2. * versatz; final
	 * double new_gauge = this.abw.getGauge() - 2. * versatz; final double
	 * new_r1_outer = this.abw.getR1() + new_gauge / 2.; final double new_r1_inner =
	 * this.abw.getR1() - new_gauge / 2.; final double new_r2_outer =
	 * this.abw.getR2() + new_gauge / 2.; final double new_r2_inner =
	 * this.abw.getR2() - new_gauge / 2.; final double new_r1_outer_flange =
	 * new_r1_outer - new_flange; final double new_r1_inner_flange = new_r1_inner +
	 * new_flange; final double new_r2_outer_flange = new_r2_outer - new_flange; //
	 * final double new_r2_inner_flange = new_r2_outer + new_flange;
	 * 
	 * // R1 (greater radius) final Point r1Center = new Point(0, r1, 0); final
	 * Point r2Center = new Point(0, -r2, 0);
	 * 
	 * final Circle r1Outer = new Circle(r1Center, new_r1_outer); final Circle
	 * r1Inner = new Circle(r1Center, new_r1_inner); final Circle r2Outer = new
	 * Circle(r2Center, new_r2_outer); final Circle r2Inner = new Circle(r2Center,
	 * new_r2_inner); final Circle r1OuterFlange = new Circle(r1Center,
	 * new_r1_outer_flange); // final Circle r1InnerFlange = new Circle(r1Center,
	 * new_r1_inner_flange); final Circle r2OuterFlange = new Circle(r2Center,
	 * new_r2_outer_flange); // final Circle r2InnerFlange = new Circle(new Point(0,
	 * -r2, 0), // new_r2_inner_flange);
	 * 
	 * final Point pHc = r2Outer.intersection(r1Outer)[0]; final Point pHr =
	 * r1Outer.intersection(r2OuterFlange)[0]; final Point pHl =
	 * r2Outer.intersection(r1OuterFlange)[0]; final double aPHc1 =
	 * VectorUtils.toPolar(pHc.getX(), pHc.getY(), r1Center)[1]; final double aPHc2
	 * = VectorUtils.toPolar(pHc.getX(), pHc.getY(), r2Center)[1]; final double
	 * aPHl1 = VectorUtils.toPolar(pHl.getX(), pHl.getY(), r1Center)[1]; final
	 * double aPHl2 = VectorUtils.toPolar(pHl.getX(), pHl.getY(), r2Center)[1];
	 * final double aPHr1 = VectorUtils.toPolar(pHr.getX(), pHr.getY(),
	 * r1Center)[1]; final double aPHr2 = VectorUtils.toPolar(pHr.getX(),
	 * pHr.getY(), r2Center)[1]; if (versatz == 0.) { this.aR = aPHl1 +
	 * VectorUtils.toAngle(rail.getVFrogLength(), new_r1_inner_flange); this.aL =
	 * aPHl2 - VectorUtils.toAngle(rail.getVFrogLength(), new_r2_outer_flange); }
	 * final double PI_2 = Math.PI / 2.; final double st1 = -PI_2 + a1; final double
	 * st2 = PI_2 - a2;
	 * 
	 * final RotDir POS = RotDir.POS;
	 * 
	 * final Arc ll1 = new Arc(r1Inner, -PI_2, st1, POS); final Arc lr1 = new
	 * Arc(r1Outer, -PI_2, aPHr1, POS); final Arc lr2 = new Arc(r1Outer, aPHc1, st1,
	 * POS); final Arc lrf = new Arc(r1OuterFlange, aPHl1, aR, POS);
	 * 
	 * final Arc rl1 = new Arc(r2Outer, aPHl2, PI_2, POS); final Arc rl2 = new
	 * Arc(r2Outer, st2, aPHc2, POS); final Arc rr1 = new Arc(r2Inner, st2, PI_2,
	 * POS); final Arc rlf = new Arc(r2OuterFlange, aL, aPHr2, POS);
	 * 
	 * final Side LEFT = Side.LEFT; final Side RIGHT = Side.RIGHT; final RailDraw
	 * _ll1 = new RailDraw(ll1, "ll1", LEFT, position, LEFT); final RailDraw _lr1 =
	 * new RailDraw(lr1, "lr1", LEFT, position, RIGHT); final RailDraw _lr2 = new
	 * RailDraw(lr2, "lr2", LEFT, position, RIGHT); final RailDraw _lrf = new
	 * RailDraw(lrf, "lrf", LEFT, position, RIGHT); final RailDraw _rl1 = new
	 * RailDraw(rl1, "rl1", RIGHT, position, LEFT); final RailDraw _rl2 = new
	 * RailDraw(rl2, "rl2", RIGHT, position, LEFT); final RailDraw _rr1 = new
	 * RailDraw(rr1, "rr1", RIGHT, position, RIGHT); final RailDraw _rlf = new
	 * RailDraw(rlf, "rlf", RIGHT, position, LEFT);
	 * 
	 * ArrayList<RailDraw> ausgabe = new ArrayList<>();
	 * 
	 * ausgabe.add(_ll1); ausgabe.add(_lr1); ausgabe.add(_lr2); ausgabe.add(_lrf);
	 * ausgabe.add(_rl1); ausgabe.add(_rl2); ausgabe.add(_rlf); ausgabe.add(_rr1);
	 * 
	 * return ausgabe; }
	 */
}
