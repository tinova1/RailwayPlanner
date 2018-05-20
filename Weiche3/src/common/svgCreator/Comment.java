package common.svgCreator;

/*
 * A comment has a string as comment text. This class represents a comment and can publish it.
 */
public class Comment extends Element {
	private final String comment;

	public Comment(final String comment) {
		this.comment = comment;
	}

	String getElement() {
		return "<!-- " + this.comment + " -->\n";
	}
}
