package svgCreator;

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
