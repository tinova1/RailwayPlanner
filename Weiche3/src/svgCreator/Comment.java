package svgCreator;

public class Comment extends Element {
	private final String comment;

	public Comment(final String comment) {
		this.comment = comment;
	}

	String getElement() {
		return "<!-- " + this.comment + " -->\n";
	}
}
