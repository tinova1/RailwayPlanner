package utils;

/*
 * Positions for rails
 *  ||||            ||||
 * =||||============||||=
 *  ||||            ||||
 * =||||============||||=
 *  ||||            ||||
 *  3201            1023
 */

public enum Positions {
	HEAD_INNER(0), FOOT_INNER(1), HEAD_OUTER(2), FOOT_OUTER(3);

	private final int index;

	private Positions(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

	public boolean isFoot() {
		return this.getIndex() == 1 || this.getIndex() == 3;
	}

	public boolean isOuter() {
		return this.getIndex() == 2 || this.getIndex() == 3;
	}
}
