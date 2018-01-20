package common.components;

import java.util.ArrayList;

import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;

public class Frog {
	private final Path pathRight;
	private final Path pathLeft;
	private final Rail r;
	private final double gauge;
	private final ArrayList<RailDraw> output = new ArrayList<>();

	public Frog(final Path pathLeft, final Path pathRight, final Rail rail, final double gauge) {
		this.pathRight = pathRight;
		this.pathLeft = pathLeft;
		this.r = rail;
		this.gauge = gauge;

		this.computeOutline();
	}

	private void computeOutline() {
		Rail r = new Rail();
		final double[] offsets = r.getOffsets();
		final String[] positions = r.getPositions();
		for (int i = 0; i < offsets.length; i++) {
			compute(offsets[i], positions[i], i == 0);
		}
	}

	private void compute(double dversatz, String position, final boolean first) {
		// dversatz nach positiv nach innen
		// versatz nach rechts
		final double versatz = this.gauge / 2. - dversatz;
		final double newFlange = this.r.getFlangeFreedom() - dversatz;
		final double versatzFlange = this.gauge / 2. - newFlange;

		output.addAll(this.leftTrackRightTongueAndFrog(versatz, versatzFlange, position, first));
		output.addAll(this.rightTrackLeftTongueAndFrog(versatz, versatzFlange, position, first));
		output.addAll(this.frogRails(versatz, position));
	}

	public ArrayList<RailDraw> getRailDraws() {
		return this.output;
	}

	private Line trimLineRightFrog;

	private ArrayList<RailDraw> leftTrackRightTongueAndFrog(final double versatz, final double versatzFlange,
			final String position, final boolean first) {
		// first: when first, frog is trimmed at given length, otherwise trimmed via
		// trimLine
		ArrayList<RailDraw> output = new ArrayList<>();
		Path frogUntrimmed = pathRight.offsetClone(-versatzFlange);
		Path tongueUntrimmed = pathLeft.offsetClone(versatz);
		Curve tongue = tongueUntrimmed.trimTwoClone(frogUntrimmed, false, true)[0];
		Path frog = (Path) tongueUntrimmed.trimTwoClone(frogUntrimmed, false, true)[1];
		if (first) {
			frog = frog.trimAtClone(this.r.getVFrogLength(), false);
			this.trimLineRightFrog = frog.orthoLineEnd();
		} else {
			frog = (Path) frog.trimClone(trimLineRightFrog, false);
		}
		output.add(new RailDraw(tongue, "TongueLeft", Side.LEFT, position, Side.RIGHT));
		output.add(new RailDraw(frog, "FrogRight", Side.RIGHT, position, Side.LEFT));
		return output;
	}

	private Line trimLineLeftFrog;

	private ArrayList<RailDraw> rightTrackLeftTongueAndFrog(final double versatz, final double versatzFlange,
			final String position, final boolean first) {
		// first: when first, frog is trimmed at given length, otherwise trimmed via
		// trimLine
		ArrayList<RailDraw> output = new ArrayList<>();
		final Path frogUntrimmed = pathLeft.offsetClone(versatzFlange);
		final Path tongueUntrimmed = pathRight.offsetClone(-versatz);
		Curve tongue = tongueUntrimmed.trimTwoClone(frogUntrimmed, false, true)[0];
		Path frog = (Path) tongueUntrimmed.trimTwoClone(frogUntrimmed, false, true)[1];
		if (first) {
			frog = frog.trimAtClone(this.r.getVFrogLength(), false);
			this.trimLineLeftFrog = frog.orthoLineEnd();
		} else {
			frog = (Path) frog.trimClone(trimLineLeftFrog, false);
		}
		output.add(new RailDraw(tongue, "TongueRight", Side.LEFT, position, Side.RIGHT));
		output.add(new RailDraw(frog, "FrogLeft", Side.RIGHT, position, Side.LEFT));
		return output;
	}

	private ArrayList<RailDraw> frogRails(final double versatz, final String position) {
		final Path leftTrackRightRail = pathLeft.offsetClone(versatz);
		final Path rightTrackLeftRail = pathRight.offsetClone(-versatz);
		final Curve[] curves = leftTrackRightRail.trimTwoClone(rightTrackLeftRail, true, true);
		ArrayList<RailDraw> output = new ArrayList<>();
		output.add(new RailDraw(curves[0], "leftTrackRightRailFrog", Side.LEFT, position, Side.RIGHT));
		output.add(new RailDraw(curves[1], "rightTrackLeftRailFrog", Side.RIGHT, position, Side.LEFT));
		return output;
	}

}
