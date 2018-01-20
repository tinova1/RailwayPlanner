package common.components;

import common.vectorMath.objects2D.Curve;

public class RailDraw {

	private Side track;// left, right
	private String name;
	private String position; // head_inner,head_outer,foot_inner,foot_outer
	private Side leftRight;// left or right rail of track?

	private Curve curve;

	public RailDraw(Curve c, String name, Side track, String position, Side leftRight) {
		curve = c;
		this.track = track;
		this.name = name;
		this.position = position;
		this.leftRight = leftRight;
	}

	public RailDraw(Curve c) {
		this.curve = c;
		this.track = Side.UNKNOWN;
		this.name = "unnamed";
		this.position = "unknown";
		this.leftRight = Side.UNKNOWN;
	}

	public Side getTrackSide() {
		return track;
	}

	public Curve getCurve() {
		return curve;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public Side getRailSide() {
		return this.leftRight;
	}

	public boolean isOuter() {
		// for Kleins not to be removed
		if (this.track == Side.LEFT && this.leftRight == Side.LEFT && this.position.equalsIgnoreCase("foot_outer")) {
			return true;
		} else if (this.track == Side.RIGHT && this.leftRight == Side.RIGHT
				&& this.position.equalsIgnoreCase("foot_outer")) {
			return true;
		} else {
			return false;
		}
	}

	public String getLeftRight() {
		// left or right position of whole Rail
		if ((this.name.endsWith("l") && this.position.endsWith("inner"))
				|| (this.name.endsWith("r") && this.position.endsWith("outer"))) {
			return "right";
		} else {
			return "left";
		}
	}
}
