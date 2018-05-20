package common.svgCreator;

import java.util.ArrayList;
import java.util.List;

import common.svgCreator.Entry;

public class Tag extends Element {
	private final String name;

	private final List<Entry> entries = new ArrayList<>();
	private final List<Element> elements = new ArrayList<>();

	public Tag(final String name) {
		this.name = name;
	}

	String getElement() {
		String output = "<" + this.name + " ";
		for (Entry e : entries) {
			output += e.getEntry() + " ";
		}
		if (this.elements.size() > 0) {
			output += ">\n";
			for (Element ele : this.elements) {
				output += "    " + ele.getElement();
			}
			output += "</" + this.name + ">";
		} else {
			output += "/>";
		}
		output += "\n";
		return output;
	}

	public void addEntry(final Entry e) {
		this.entries.add(e);
	}

	public void addEntry(final String name, final String val) {
		this.addEntry(new Entry(name, val));
	}

	// add Tag or Comment
	public void addElement(final Element e) {
		this.elements.add(e);
	}

	public void addComment(final String comment) {
		this.elements.add(new Comment(comment));
	}
}
