package common.components;

public enum Side {
	LEFT("left"), RIGHT("right"), BOTH(""), UNKNOWN("?");
	private final String string;

	private Side(String string) {
		this.string = string;
	}

	public String getString() {
		return this.string;
	}
}
