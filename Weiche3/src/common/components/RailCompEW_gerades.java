package common.components;

import java.util.ArrayList;

import common.components.RailDraw;
import common.model.ew.PathTurnout;
import common.vectorMath.objects2D.Path;

public class RailCompEW_gerades {

	private PathTurnout w;
	private Rail r;

	public RailCompEW_gerades(PathTurnout w) {
		this.w = w;
		this.r = w.getRail();
	}

	public ArrayList<RailDraw> computeOutline() {
		ArrayList<RailDraw> output = new ArrayList<>();
		final double[] offsets = r.getOffsets();
		final String[] positions = r.getPositions();
		for (int i = 0; i < offsets.length; i++) {
			final boolean isFirst = (i == 0);
			ArrayList<RailDraw> computed = compute(offsets[i], positions[i], isFirst);
			output.addAll(computed);
		}
		final Frog frog = new Frog(w.getPathLeft(), w.getPathRight(), w.getRail(), w.getGauge());
		output.addAll(frog.getRailDraws());
		return output;
	}

	private ArrayList<RailDraw> compute(double dversatz, String position, final boolean first) {
		// dversatz nach positiv nach innen
		// versatz nach rechts
		final double versatz = this.w.getGauge() / 2. - dversatz;

		ArrayList<RailDraw> output = new ArrayList<>();
		output.addAll(this.leftTrackLeftRail(versatz, position));
		output.addAll(this.rightTrackRightRail(versatz, position));
		return output;
	}

	private ArrayList<RailDraw> leftTrackLeftRail(final double versatz, final String position) {
		ArrayList<RailDraw> output = new ArrayList<RailDraw>();
		Path current = w.getPathLeft().offsetClone(-versatz);
		output.add(new RailDraw(current, "leftOuter", Side.LEFT, position, Side.LEFT));
		return output;
	}

	private ArrayList<RailDraw> rightTrackRightRail(final double versatz, final String position) {
		ArrayList<RailDraw> output = new ArrayList<RailDraw>();
		final Path current = w.getPathRight().offsetClone(versatz);
		output.add(new RailDraw(current, "rightOuter", Side.RIGHT, position, Side.RIGHT));
		return output;
	}
}
