package common.components;

import java.util.List;

import common.io.Import_obj;
import common.svgCreator.Tag;
import common.vectorMath.Orientation;
import common.vectorMath.objects3D.Cube;
import common.vectorMath.objects3D.Polyhedron;

public class Kleineisen {
	private Polyhedron poly;
	private RailDraw rail;
	private Tie tie;
	private String type;

	public Kleineisen(RailDraw rail, Tie tie, String type) {
		String fileName;
		if (type.equalsIgnoreCase("small")) {
			fileName = "KleineisenKlein.obj";
			this.type = type;
		} else {
			fileName = "KleineisenPfeil.obj";
			this.type = type;
		}
		this.poly = Import_obj.import_obj(fileName);
		this.rail = rail;
		this.tie = tie;
	}

	public List<Tag> export_svg() {
		return this.poly.export_svg();
		/*
		 * final String x = this.getPoly().getCSYS().getX() + ""; final String y =
		 * this.getPoly().getCSYS().getY() + ""; final double rotationDeg =
		 * Math.toDegrees(this.getPoly().getCSYS().getRot()[2]);
		 * 
		 * final Tag imgTag = new Tag("image"); imgTag.addEntry("xlink:href",
		 * "Kleineisen.svg"); imgTag.addEntry("x", x); imgTag.addEntry("y", y);
		 * imgTag.addEntry("width", "15mm"); imgTag.addEntry("height", "15mm"); //
		 * rotate(rotationDeg,x,y) imgTag.addEntry("transform", "rotate(" + rotationDeg
		 * + "," + x + "," + y + ")"); return imgTag;
		 */
	}

	public Polyhedron getPoly() {
		return this.poly;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
		// this.poly=
	}

	public Cube getBoundingBox() {
		return this.poly.boundingBox(Orientation.GLOBAL);
	}

	public RailDraw getRail() {
		return rail;
	}

	public Tie getTie() {
		return tie;
	}
}
