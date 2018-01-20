package common.svgCreator;

import java.util.ArrayList;

public class SVGFile {
	private ArrayList<Element> elements = new ArrayList<>();
	private final String header = "<?xml version=\"1.0\"?>\r\n" + //
			"<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\r\n" + //
			"  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";

	public SVGFile() {

	}

	public String getFile() {
		String output = header + "\n\n";
		for (Element ele : this.elements) {
			output += ele.getElement();
		}
		return output;
	}

	public void addElement(final Element e) {
		this.elements.add(e);
	}
}
