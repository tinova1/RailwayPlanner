package model.ew;


import model.railway.Railway;


public class BW extends Railway {
	/*
	 * protected final double r1; protected final double r2; protected final double
	 * a1; protected final double a2;
	 * 
	 * protected BW(double gauge, Rail r, double r1, double r2, double a1, double
	 * a2) { super(new CSYS(), new Tie(), r, gauge); if (r1 < 0. || r2 < 0.) {
	 * System.out.println("Fehler! Radien müssen größer Null sein!");
	 * System.exit(1); } if (r1 < r2) {
	 * System.out.println("Fehler! R1 muss größer als R2 sein!"); System.exit(1); }
	 * 
	 * this.r1 = r1; this.r2 = r2; this.a1 = a1; this.a2 = a2;
	 * 
	 * }
	 * 
	 * public double getR1() { return this.r1; }
	 * 
	 * public double getR2() { return this.r2; }
	 * 
	 * public double getA1() { return this.a1; }
	 * 
	 * public double getA2() { return this.a2; }
	 * 
	 * protected void calcTies() {
	 * 
	 * this.tieBand.add(TieBandComp.bw(this, 0, "test"));
	 * 
	 * // R1 final Tie lastTurnoutTie = this.lastTie(); final double lBeginn =
	 * beginnArcLength(lastTurnoutTie) + lastTurnoutTie.getDist(); final double
	 * aBeginnR1 = MathUtils.toAngle(lBeginn, r1);
	 * 
	 * double r1 = this.r1; if (this instanceof ABW) { r1 *= -1.; }
	 * this.tieBand.add(TieBandComp.curve(aBeginnR1, this.a1, r1,
	 * lastTie().getNumber() + 1, "R1", this.t)); // r2 final double aBeginnR2 =
	 * MathUtils.toAngle(lBeginn, r2);
	 * 
	 * this.tieBand.add(TieBandComp.curve(aBeginnR2, this.a2, this.r2,
	 * lastTie().getNumber() + 1, "R2", this.t)); }
	 * 
	 * protected double beginnArcLength(final Tie lastTie) { final Circle _r2 = new
	 * Circle(new Point(0, -this.r2, 0), this.r2); final Line line =
	 * lastTie.getLine(); final Point inters = line.intersection(_r2)[0]; final
	 * double xMax = inters.getX(); final double yMax = inters.getY(); // final
	 * Orientation G = Orientation.GLOBAL; // final double xMax =
	 * lastTie.getCube().max(G)[0]; // final double yMax =
	 * lastTie.getCube().max(G)[1]; final double phi = Math.PI / 2. +
	 * VectorUtils.toPolar(xMax, yMax, 0, r1)[1]; return MathUtils.toLength(phi, r1)
	 * + lastTie.getDist(); }
	 * 
	 * protected Tie lastTie() { try { return
	 * tieBand.getTieList().get(tieBand.getTieList().size() - 1); } catch
	 * (IndexOutOfBoundsException e) {
	 * System.out.println("Fehler: lastTie nicht vorhanden"); return null; } }
	 */

}
