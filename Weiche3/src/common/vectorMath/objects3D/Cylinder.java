package common.vectorMath.objects3D;

public class Cylinder extends Polyhedron {
	double r; // radius
	double h; // height
	int v; // number of verticies in one circle

	public Cylinder(double r, double h, int v) {
		super();
		this.r = r;
		this.h = h;
		this.v = v;
		double dphi = 2. * Math.PI / (double) v;
		for (int i = 0; i < v; i++) {
			// lower circle
			Point p = new Point(r, 0., 0.);

			p.rotate(dphi * (double) i);

			verts.add(p);
			// odd numbers / indices go to upper circle
			verts.add(new Point(p.getX(), p.getY(), p.getZ() + h));
		}
	}

	public String export_obj() {
		String ausgabe = "";
		// exporting verts
		for (int i = 0; i < verts.size(); i++) {
			Point e = verts.get(i);
			ausgabe += "v " + e.getX() + " " + e.getY() + " " + e.getZ() + "\n";
		}
		// exporting faces, lower circle
		ausgabe += "f ";
		for (int i = 0; i < verts.size(); i++) {
			if (i % 2 == 0) {
				ausgabe += -(verts.size() - i) + " ";
			}
		}
		ausgabe += "\n";

		// exporting faces, upper circle
		ausgabe += "f ";
		for (int i = 0; i < verts.size(); i++) {
			if (i % 2 != 0) {
				ausgabe += -(verts.size() - i) + " ";
			}
		}
		ausgabe += "\n";

		// exporting side faces
		for (int i = 0; i < verts.size(); i++) {
			int[] c = new int[4]; // index of corners
			c[0] = -(verts.size() - i); // down right
			c[1] = c[0] + 1; // up right
			c[2] = c[0] + 3; // up left
			c[3] = c[0] + 2; // down left
			for (int j = 0; j < c.length; j++) {
				if (c[j] >= 0)
					c[j] -= verts.size();
			}
			ausgabe += "f " + c[0] + " " + c[1] + " " + c[2] + " " + c[3] + "\n";

		}
		return ausgabe;
	}
}