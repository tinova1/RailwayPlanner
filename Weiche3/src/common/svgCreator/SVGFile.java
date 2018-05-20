package common.svgCreator;

import java.util.ArrayList;
import java.util.List;

/*
 * This makes an abstract SVG file that has Elements (Tags and comments)
 * The full file is published as string
 */
public class SVGFile {
	private List<Element> elements = new ArrayList<>();
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
