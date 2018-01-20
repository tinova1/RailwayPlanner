package common.io;

import java.util.List;

import common.components.Kleineisen;
import common.components.RailDraw;
import common.components.Tie;
import common.model.railway.Railway;
import common.svgCreator.Entry;
import common.svgCreator.SVGExporter;
import common.svgCreator.SVGFile;
import common.svgCreator.Tag;
import common.vectorMath.Orientation;

public class Export_svg {

	private final SVGFile svgFile = new SVGFile();

	private final Railway w;
	private double xMax = -Double.MAX_VALUE;
	private double xMin = Double.MAX_VALUE;
	private double yMax = -Double.MAX_VALUE;
	private double yMin = Double.MAX_VALUE;

	public Export_svg(final Railway w) {
		this.w = w;
	}

	public void ausgabe() {
		this.createFile();
		SVGExporter.writeFile(svgFile, "ausgabe.svg");
	}

	private void createFile() {
		final Tag rootTag = new Tag("svg", false);
		rootTag.addEntry("version", "1.1");
		rootTag.addEntry("xmlns", "http://www.w3.org/2000/svg");
		rootTag.addEntry("transform", "scale(1,-1)");
		svgFile.addElement(rootTag);

		final Tag gTag = new Tag("g", false);
		gTag.addEntry("stroke", "black");
		gTag.addEntry("stroke-width", "0.1");
		gTag.addEntry("fill", "none");

		List<Tie> tieList = w.getTieBand().getTieList();
		for (Tie i : tieList) {
			gTag.addComment("Tie");
			gTag.addElement(i.export_svg());
			for (Kleineisen k : i.getKleinList()) {
				gTag.addComment("Kleineisen");
				gTag.addElement(k.export_svg());
			}
			this.setNewBounds(i);
		}
		this.setMargin();

		List<RailDraw> railList = w.getRailList();
		for (RailDraw r : railList) {
			gTag.addComment("Rail " + r.hashCode());
			gTag.addElement(r.getCurve().export_svg());
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
		final double margin = 10;
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
	/*
	 * public static void ausgabe(Railway w) { try { FileWriter ausgabe = new
	 * FileWriter(new File("ausgabe.svg")); File header = new File("HeaderNeu.txt");
	 * File footer = new File("FooterNeu.txt"); Scanner sc_header = new
	 * Scanner(header); Scanner sc_footer = new Scanner(footer); while
	 * (sc_header.hasNextLine()) { ausgabe.write(sc_header.nextLine() + "\n"); }
	 * ausgabe.
	 * write("<g stroke = \"black\" stroke-width = \"0.1\" fill = \"none\">\n");
	 * 
	 * ausgabe.write("</g>\n");
	 * 
	 * while (sc_footer.hasNextLine()) { ausgabe.write(sc_footer.nextLine() + "\n");
	 * } sc_header.close(); sc_footer.close(); ausgabe.close(); } catch (IOException
	 * e) { e.getLocalizedMessage(); } }
	 * 
	 * private ArrayList<String> createBody() { ArrayList<String> body = new
	 * ArrayList<>();
	 * 
	 * // /////////// Ties ArrayList<Tie> tieList = w.getTieBand().getTieList();
	 * 
	 * for (Tie i : tieList) { body.add(i.export_svg()); xMax = Math.max(xMax,
	 * i.getCube().max(Orientation.GLOBAL)[0]); yMax = Math.max(yMax,
	 * i.getCube().max(Orientation.GLOBAL)[1]); xMin = Math.min(xMin,
	 * i.getCube().min(Orientation.GLOBAL)[0]); yMin = Math.min(yMin,
	 * i.getCube().min(Orientation.GLOBAL)[1]); }
	 * 
	 * ///////// Rail ArrayList<RailDraw> rail_list = w.getRailList(); for (int j =
	 * 0; j < rail_list.size(); j++) { Curve i = rail_list.get(j).getCurve();
	 * 
	 * body.add(i.export_svg());
	 * 
	 * }
	 * 
	 * for (int i = 0; i < tieList.size(); i++) { Tie t = tieList.get(i);
	 * ArrayList<Kleineisen> kleinList = t.getKleinList(); for (int j = 0; j <
	 * kleinList.size(); j++) { Kleineisen k = kleinList.get(j);
	 * body.add(k.getPoly().export_svg()); } }
	 * 
	 * final double rimOffset = 10; xMax += rimOffset; xMin -= rimOffset; yMax +=
	 * rimOffset; yMin -= rimOffset; return body; }
	 */
}