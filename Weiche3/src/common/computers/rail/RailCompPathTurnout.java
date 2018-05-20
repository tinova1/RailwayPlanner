package common.computers.rail;

import java.util.ArrayList;
import java.util.List;

import common.components.Rail;
import common.components.RailDraw;
import common.railway.ew.PathTurnout;
import common.vectorMath.objects2D.Path;
import utils.Positions;
import utils.Side;

public class RailCompPathTurnout {

	private PathTurnout w;
	private Rail r;

	public RailCompPathTurnout(PathTurnout w) {
		this.w = w;
		this.r = w.getRail();
	}

	public ArrayList<RailDraw> computeOutline() {
		ArrayList<RailDraw> output = new ArrayList<>();
		final double[] offsets = r.getOffsets();
		for (int i = 0; i < offsets.length; i++) {
			final boolean isFirst = (i == 0);
			List<RailDraw> computed = compute(offsets[i], Positions.values()[i], isFirst);
			output.addAll(computed);
		}
		final Frog frog = new Frog(w.getPathLeft(), w.getPathRight(), w.getRail(), w.getGauge());
		output.addAll(frog.getRailDraws());
		return output;
	}

	private List<RailDraw> compute(double dversatz, Positions position, final boolean first) {
		// dversatz nach positiv nach innen
		// versatz nach rechts
		final double versatz = this.w.getGauge() / 2. - dversatz;

		List<RailDraw> output = new ArrayList<>();
		output.addAll(this.leftTrackLeftRail(versatz, position));
		output.addAll(this.rightTrackRightRail(versatz, position));
		return output;
	}

	private ArrayList<RailDraw> leftTrackLeftRail(final double versatz, final Positions position) {
		ArrayList<RailDraw> output = new ArrayList<RailDraw>();
		Path current = w.getPathLeft().offsetClone(-versatz);
		// left outer
		output.add(new RailDraw(current, Side.LEFT, position, Side.LEFT));
		return output;
	}

	private ArrayList<RailDraw> rightTrackRightRail(final double versatz, final Positions position) {
		ArrayList<RailDraw> output = new ArrayList<RailDraw>();
		final Path current = w.getPathRight().offsetClone(versatz);
		// right outer
		output.add(new RailDraw(current, Side.RIGHT, position, Side.RIGHT));
		return output;
	}
}
