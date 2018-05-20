package common.svgCreator;
/*
 * An entry for a tag, like this: 
 * stroke="black"
 * It has a name (stroke) and a value (black). Both are saved as variables
 */
public class Entry {
	private final String name;
	private final String value;

	public Entry(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public String getEntry() {
		return this.name + "=\"" + this.value + "\"";
	}
}
