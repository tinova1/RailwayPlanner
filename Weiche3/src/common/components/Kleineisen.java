package common.components;

import common.io.Import_obj;
import common.svgCreator.Tag;
import common.vectorMath.Orientation;
import common.vectorMath.Polyhedron;
import common.vectorMath.objects3D.Cube;

public class Kleineisen {
	private Polyhedron poly;
	private RailDraw rail;
	private Tie tie;
	private boolean active = true;
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

	public Tag export_svg() {
		return this.getPoly().export_svg();
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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
