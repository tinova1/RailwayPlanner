package common.vectorMath.objects3D;

import java.util.ArrayList;
import java.util.List;

import common.io.IO_obj_Utils;
import common.svgCreator.Tag;
import common.vectorMath.Orientation;
import common.vectorMath.VectorUtils;
import common.vectorMath.objects2D.Path;
import dddEngine.DDDPolygon;

public class Polyhedron {

	private static final Orientation global = Orientation.GLOBAL;
	private static final Orientation local = Orientation.LOCAL;

	protected List<int[]> faces = new ArrayList<>();
	protected List<Point> verts = new ArrayList<Point>();
	protected CSYS csys = new CSYS();
	
	private List<Tag> tagLocal = new ArrayList<>();

	public Polyhedron() {
	}

	public Polyhedron(List<Point> verts, List<int[]> faces) {
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

	public void move(final double dx, final double dy, final double dz) {
		this.getCSYS().move(dx, dy, dz);
	}

	public void move(final double dist, final double angle) {
		this.getCSYS().getPoint().move(dist, angle);
	}

	public void move(Point dp) {
		this.getCSYS().getPoint().move(dp);
	}

	public List<Point> getVerts(Orientation o) {
		if (o == local)
			return this.verts;
		else {
			return this.getVertsGlobal();
		}
	}

	private List<Point> getVertsGlobal() {
		ArrayList<Point> vertsGlobal = new ArrayList<Point>();
		for (int i = 0; i < verts.size(); i++) {
			vertsGlobal.add(i, verts.get(i).clone());
			vertsGlobal.get(i).scale(this.getCSYS().getScale());
			vertsGlobal.get(i).rotate(new Point(0, 0, 0), this.csys.getRot());
			vertsGlobal.get(i).move(this.csys.getPoint());
		}
		return vertsGlobal;
	}

	public List<int[]> getFaces() {
		return faces;
	}

	public List<int[]> getReverseFaces() {
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
		int count = getVerts(global).size();
		for (int i = 0; i < count; i++) {
			output += IO_obj_Utils.pointToLine(getVerts(global).get(i));
		}
		count = faces.size();
		for (int i = 0; i < count; i++) {
			output += IO_obj_Utils.faceToLine(getReverseFaces().get(i));
		}
		return output + "\n";
	}

	public double getZRot() {
		return this.csys.getRot()[2];
	}

	public void updateTagLocal() {
		final Path[] facesAsPaths = new Path[this.faces.size()]; // for each face, one path is created
		final List<Point> vertsGl = this.getVerts(local); // shorten access to Pointer
		for (int i = 0; i < facesAsPaths.length; i++) {
			final int[] thisFace = this.faces.get(i);
			facesAsPaths[i] = new Path();
			if (thisFace.length < 2) // face must have at least 2 verts, otherwise continue
				continue;
			// iterate over face
			for (int j = 0; j < thisFace.length; j++) {
				final Point p1 = vertsGl.get(thisFace[j] - 1);
				final Point p2 = vertsGl.get(thisFace[(j + 1) % thisFace.length] - 1);
				// ignore line if length in xy plane is zero
				//if (Dist.distInXYPlane2(p1, p2) < MathUtils.ROUND_ZERO)
					//continue;
				final LineSeg seg = new LineSeg(p1, p2);
				facesAsPaths[i].add(seg);
			}
		}
		final List<Tag> output = new ArrayList<>();
		for (int i = 0; i < facesAsPaths.length; i++) {
			final Tag tag = facesAsPaths[i].export_svg();
			if (tag != null)
				output.add(tag);
		}
		this.tagLocal=output;
	}

	public List<Tag> export_svg() {
		final List<Tag> local = this.tagLocal;
		final double dx = this.csys.getX();
		final double dy = this.csys.getY();
		final double rot = Math.toDegrees(this.csys.getRot()[2]);
		final double[] sc = this.csys.getScale();
		for (Tag tag : local) {
			// like this: "translate(30) rotate(45 50 50) scale(1 1)"
			final String entryValue = "translate(" + dx + " " + dy + ") rotate(" + rot + ") scale(" + sc[0] + " "
					+ sc[1] + ")";
			// like this: transform="translate(30) rotate(45 50 50) scale(1 1)"
			tag.addEntry("transform", entryValue);
			tag.addEntry("vector-effect", "non-scaling-stroke");
		}
		return local; // local is now global
	}

	// maximum value in each axis
	public double[] max(Orientation o) {
		double[] output = new double[3];
		// initialize ausgabe with minimum values
		for (int i = 0; i < output.length; i++)
			output[i] = -Double.MAX_VALUE;

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
		// initialize output with maximum values
		for (int i = 0; i < output.length; i++)
			output[i] = Double.MAX_VALUE;

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
		final Point minPoint = new Point(min);
		final Point maxPoint = new Point(max);
		final Point center = VectorUtils.middle(minPoint, maxPoint);
		final CSYS c = new CSYS(center, dimensions, new double[3]);
		return new Cube(c, 0);
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

	public DDDPolygon[] getPolygons() {
		/*
		 * Return Faces as DDDPolygons
		 */
		DDDPolygon[] ausgabe = new DDDPolygon[this.faces.size()];
		for (int i = 0; i < this.faces.size(); i++) {
			Point[] vertsOfPoly = new Point[this.faces.get(i).length];
			for (int j = 0; j < vertsOfPoly.length; j++) {
				final int[] thisFace = this.faces.get(i);
				final int thisVertNumber = thisFace[j];
				final Point thisVert = this.getVerts(Orientation.GLOBAL).get(thisVertNumber - 1);
				vertsOfPoly[j] = thisVert;
			}
			ausgabe[i] = new DDDPolygon(vertsOfPoly);
		}
		return ausgabe;

	}

}
