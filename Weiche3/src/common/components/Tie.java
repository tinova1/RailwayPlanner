package common.components;

import java.util.ArrayList;
import java.util.List;

import common.svgCreator.Tag;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Cube;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Tie {

	private final CSYS csys;

	private double length = 20.;
	private double wid = 2.; // width
	private double dist = 5.; // distance
	private double height = 1.;
	private double longest = 40.;// length of longest tie

	private double arcLength;
	private int number;

	private ArrayList<Kleineisen> kleinList = new ArrayList<>();

	private Cube cube;

	public Tie(final Point p, double l, int number) {
		this.length = l;
		this.csys = new CSYS(p, //
				new double[] { wid, length, height }, //
				new double[] { 0, 0, 0 });
		this.cube = new Cube(this.csys, 2);
		this.number = number;
	}

	public Tie(int number) {
		this.csys = new CSYS(new Point(), new double[] { wid, length, height }, new double[3]);
		cube = new Cube(this.csys, 2);
		this.number = number;
	}

	public Tie() {
		this.csys = new CSYS(new Point(0, 0), new double[] { 0, 0, 0 }, new double[] { 0, 0, 0 });
	}

	public ArrayList<Kleineisen> getKleinList() {
		return this.kleinList;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return wid;
	}

	public double getDist() {
		return dist;
	}

	public double getHeight() {
		return height;
	}

	public double getLongest() {
		return longest;
	}

	public Cube getCube() {
		return cube;
	}

	public CSYS getCSYS() {
		return this.csys;
	}

	public Point[] endPoints() {
		final Point pLeft = this.csys.getPoint().clone();
		pLeft.move(this.length / 2., this.csys.getRot()[2] + Math.PI / 2.);
		final Point pRight = this.csys.getPoint().clone();
		pRight.move(-this.length / 2., this.csys.getRot()[2] + Math.PI / 2.);
		return new Point[] { pLeft, pRight };
	}

	// represents Tie as line to compute Kleineisen
	public LineSeg getLineSeg() {
		return new LineSeg(this.endPoints()[0], this.endPoints()[1]);
	}

	public int getNumber() {
		return number;
	}

	public void setLength(final double newLength) {
		final double yScale = newLength / this.getLength();
		this.getCSYS().scale(new double[] { 1, yScale, 1 });
	}

	public void rotate(final double rot) {
		this.getCSYS().rotate(this.getCSYS().getPoint(), new double[] { 0, 0, rot });
	}

	public double getArcLength() {
		return this.arcLength;
	}

	public void setArcLength(double arcLength) {
		this.arcLength = arcLength;
	}

	public List<Tag> export_svg() {
		return this.cube.export_svg();
	}
}
