package common.svgCreator;

import java.util.ArrayList;

import common.svgCreator.Entry;

public class Tag extends Element {
	private final String name;
	private final boolean closed;

	private final ArrayList<Entry> entries = new ArrayList<>();
	private final ArrayList<Element> elements = new ArrayList<>();

	public Tag(final String name, final boolean closed) {
		this.name = name;
		this.closed = closed;
	}

	public String getElement() {
		String output = "<" + this.name + " ";
		for (Entry e : entries) {
			output += e.getEntry() + " ";
		}
		if (!closed) {
			output += ">\n";
			for (Element ele : elements) {
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
