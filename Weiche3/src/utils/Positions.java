package utils;

public enum Positions {
	head_inner(0), foot_inner(1), head_outer(2), foot_outer(3);

	private final int index;

	private Positions(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}
