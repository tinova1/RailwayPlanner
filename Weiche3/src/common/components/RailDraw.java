package common.components;

import common.vectorMath.objects2D.Curve;
import utils.Positions;
import utils.Side;

public class RailDraw {

	private Side track;// left, right turnout way
	private Positions position;
	private Side leftRight;// left or right rail of track?

	private Curve curve;

	public RailDraw(Curve c, Side track, final Positions position, Side leftRight) {
		this.curve = c;
		this.track = track;
		this.position = position;
		this.leftRight = leftRight;
	}

	public Side getTrackSide() {
		return track;
	}

	public Curve getCurve() {
		return curve;
	}

	public Positions getPosition() {
		return position;
	}

	public Side getRailSide() {
		return this.leftRight;
	}
	
	/*
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
	*/
}
