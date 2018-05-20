package common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import common.components.Kleineisen;
import common.components.RailDraw;
import common.components.Tie;
import common.geometry.Canvas;
import common.railway.Railway;
import common.svgCreator.Entry;
import common.svgCreator.SVGFile;
import common.svgCreator.Tag;
import common.vectorMath.Orientation;

public class Export_svg {

	private final double margin = 10;

	private final SVGFile svgFile = new SVGFile();

	private final Canvas canvas;
	private double xMax = -Double.MAX_VALUE;
	private double xMin = Double.MAX_VALUE;
	private double yMax = -Double.MAX_VALUE;
	private double yMin = Double.MAX_VALUE;

	public Export_svg(final Canvas canvas) {
		this.canvas = canvas;
	}

	public void ausgabe(final String fileName) {
		this.createFile();
		try {
			FileWriter writer = new FileWriter(new File(fileName + ".svg"));
			writer.write(svgFile.getFile());
			writer.close();
		} catch (IOException e) {
			e.getLocalizedMessage();
		}
	}

	private void createFile() {
		final Tag rootTag = new Tag("svg");
		rootTag.addEntry("version", "1.1");
		rootTag.addEntry("xmlns", "http://www.w3.org/2000/svg");
		rootTag.addEntry("xmlns:xlink", "http://www.w3.org/1999/xlink");
		rootTag.addEntry("transform", "scale(1,-1)");
		svgFile.addElement(rootTag);

		final Tag gTag = new Tag("g");
		gTag.addEntry("stroke", "black");
		gTag.addEntry("stroke-width", "0.1");
		gTag.addEntry("fill", "none");

		for (Railway w : canvas.getRailwayList()) {
			///////
			// Ties
			///////
			List<Tie> tieList = w.getTieBand().getTieList();
			for (Tie i : tieList) {
				gTag.addComment("Tie");
				List<Tag> elements = i.export_svg();
				for (Tag ele : elements)
					gTag.addElement(ele);
				this.setNewBounds(i);
			}
			this.setMargin();
			///////
			// Rails
			///////
			List<RailDraw> railList = w.getRailList();
			for (RailDraw r : railList) {
				gTag.addComment("Rail " + r.hashCode());
				gTag.addElement(r.getCurve().export_svg());
			}
			///////
			// Kleineisen
			///////
			/*
			for (Tie i : tieList) {
				for (Kleineisen k : i.getKleinList()) {
					gTag.addComment("Kleineisen");
					List<Tag> elements = k.export_svg();
					for (Tag ele : elements)
						gTag.addElement(ele);
				}
			}*/
		}

		rootTag.addEntry(this.widthEntry());
		rootTag.addEntry(this.heightEntry());
		rootTag.addEntry(this.viewBoxEntry());
		rootTag.addElement(gTag);
	}

	private void setNewBounds(final Tie i) {
		xMax = Math.max(xMax, i.getCube().max(Orientation.GLOBAL)[0]);
		yMax = Math.max(yMax, i.getCube().max(Orientation.GLOBAL)[1]);
		xMin = Math.min(xMin, i.getCube().min(Orientation.GLOBAL)[0]);
		yMin = Math.min(yMin, i.getCube().min(Orientation.GLOBAL)[1]);
	}

	private void setMargin() {
		xMax += margin;
		yMax += margin;
		yMin -= margin;
		xMin -= margin;
	}

	private Entry viewBoxEntry() {
		final double xMin = this.xMin;
		final double yMin = this.yMin;
		final double width = this.xMax - this.xMin;
		final double height = this.yMax - this.yMin;
		return new Entry("viewBox", xMin + " " + yMin + " " + width + " " + height);
	}

	private Entry widthEntry() {
		final double width = this.xMax - this.xMin;
		return new Entry("width", width + "mm");
	}

	private Entry heightEntry() {
		final double height = this.yMax - this.yMin;
		return new Entry("height", height + "mm");
	}
}