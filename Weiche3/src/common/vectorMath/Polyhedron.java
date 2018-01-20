package common.vectorMath;

import java.util.ArrayList;

import common.io.io_obj;
import common.svgCreator.Tag;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Axis;
import common.vectorMath.objects3D.CSYS;
import common.vectorMath.objects3D.Cube;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Polyhedron {

	private static final Orientation global = Orientation.GLOBAL;
	private static final Orientation local = Orientation.LOCAL;

	protected ArrayList<int[]> faces = new ArrayList<>();
	protected ArrayList<Point> verts = new ArrayList<Point>();
	protected CSYS csys = new CSYS();

	public Polyhedron() {
	}

	public Polyhedron(ArrayList<Point> verts, ArrayList<int[]> faces) {
		this.verts = verts;
		this.faces = faces;
	}

	public void rotateZ(double zrot) {
		this.getCSYS().rotate(new double[] { 0, 0, zrot });
	}

	public CSYS getCSYS() {
		return csys;
	}

	public void moveVerts(Point dp) {
		for (int i = 0; i < verts.size(); i++) {
			verts.get(i).move(dp);
		}
	}

	public void scaleLocal(Point factors) {
		for (Point p : verts) {
			p.scale(factors);
		}
	}

	public void rotateVerts(double zrot) {
		for (int i = 0; i < verts.size(); i++) {
			verts.get(i).rotate(zrot);
		}
	}

	public void rotate(double[] rot) {
		this.getCSYS().rotate(rot);
	}

	public void move(Point dp) {
		this.getCSYS().getPoint().move(dp);
	}

	public ArrayList<Point> getVerts(Orientation o) {
		if (o == local)
			return this.verts;
		else {
			return this.getVertsGlobal();
		}
	}

	private ArrayList<Point> getVertsGlobal() {
		ArrayList<Point> vertsGlobal = new ArrayList<Point>();
		for (int i = 0; i < verts.size(); i++) {
			vertsGlobal.add(i, verts.get(i).clone());
			vertsGlobal.get(i).scale(this.getCSYS().getScale());
			vertsGlobal.get(i).rotate(new Point(0, 0, 0), this.csys.getRot());
			vertsGlobal.get(i).move(this.csys.getPoint());
		}
		return vertsGlobal;
	}

	public ArrayList<int[]> getFaces() {
		return faces;
	}

	public ArrayList<int[]> getReverseFaces() {
		int counter = this.verts.size();
		ArrayList<int[]> reverseFaces = new ArrayList<>();
		for (int i = 0; i < faces.size(); i++) {
			int[] face = faces.get(i);
			int[] revFace = new int[face.length];
			for (int j = 0; j < face.length; j++) {
				revFace[j] = -counter + face[j] - 1;
			}
			reverseFaces.add(revFace);
		}
		return reverseFaces;
	}

	public String export_obj() {
		String output = "";
		for (int i = 0; i < getVerts(global).size(); i++) {
			output += io_obj.pointToLine(getVerts(global).get(i));
		}
		for (int i = 0; i < faces.size(); i++) {
			output += io_obj.faceToLine(getReverseFaces().get(i));
		}
		return output + "\n";
	}

	public double getZRot() {
		return this.csys.getRot()[2];
	}

	public Tag export_svg() {
		Path output = new Path();
		ArrayList<Point> vertsGl = this.getVerts(global);
		for (int i = 0; i < this.faces.size(); i++) {
			int[] face = this.faces.get(i);
			for (int j = 0; j < face.length; j++) {
				LineSeg l = new LineSeg(vertsGl.get(j), vertsGl.get((j + 1) % face.length));
				if (l.getDir().getLength2() > 1e-13)
					output .add(l);
			}
		}
		return output.export_svg();
	}

	// maximum value in each axis
	public double[] max(Orientation o) {
		double[] output = new double[3];
		// initialize ausgabe with minimum values
		for (int i = 0; i < output.length; i++) {
			output[i] = -Double.MAX_VALUE;
		}
		for (int i = 0; i < this.getVerts(o).size(); i++) {
			double[] xyz = this.getVerts(o).get(i).getxyz();
			for (int j = 0; j < xyz.length; j++) {
				output[j] = Double.max(output[j], xyz[j]);
			}
		}
		return output;
	}

	// minimum value in each axis
	public double[] min(Orientation o) {
		double[] output = new double[3];
		// initialize ausgabe with maximum values
		for (int i = 0; i < output.length; i++) {
			output[i] = Double.MAX_VALUE;
		}
		for (int i = 0; i < this.getVerts(o).size(); i++) {
			double[] xyz = this.getVerts(o).get(i).getxyz();
			for (int j = 0; j < xyz.length; j++) {
				output[j] = Double.min(output[j], xyz[j]);
			}
		}
		return output;
	}

	public Cube boundingBox(Orientation o) {
		final double[] min = this.min(o);
		final double[] max = this.max(o);
		final double[] dimensions = new double[3];
		for (int i = 0; i < dimensions.length; i++) {
			dimensions[i] = max[i] - min[i];
		}
		final CSYS c = new CSYS(new Point(min), dimensions, new double[3]);
		return new Cube(c, 1);
	}

	public Point outermost(final Axis axis, Orientation o) {
		final double minVal = -Double.MAX_VALUE;
		final int axisNo = axis.getNo();
		Point min = new Point(minVal, minVal, minVal);
		for (Point p : this.getVerts(o)) {
			if (p.getxyz()[axisNo] > min.getxyz()[axisNo]) {
				min = p;
			}
		}
		return min;
	}

	public Point innermost(final Axis axis, Orientation o) {
		final double maxVal = Double.MAX_VALUE;
		final int axisNo = axis.getNo();
		Point max = new Point(maxVal, maxVal, maxVal);
		for (Point p : this.getVerts(o)) {
			if (p.getxyz()[axisNo] < max.getxyz()[axisNo]) {
				max = p;
			}
		}
		return max;
	}

}
