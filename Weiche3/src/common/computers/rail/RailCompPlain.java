package common.computers.rail;

import java.util.ArrayList;
import java.util.List;

import common.components.Rail;
import common.components.RailDraw;
import common.vectorMath.objects2D.Path;
import utils.Positions;
import utils.Side;

/*
 * Creates Rails for plain railway, either straight or curved
 */

public abstract class RailCompPlain {

	public static List<RailDraw> path(final Path p, final double gauge, final Rail r, final Side side) {
		final List<RailDraw> output = new ArrayList<>();
		if (side == Side.BOTH) {
			output.addAll(path(p, gauge, r, Side.LEFT));
			output.addAll(path(p, gauge, r, Side.RIGHT));
		} else {
			double[] offsets = r.getOffsets();
			for (int i = 0; i < offsets.length; i++) {
				double offset = -offsets[i] + gauge / 2.;
				if (side == Side.LEFT)
					offset *= -1.;

				final Positions position = Positions.values()[i];
				final Path path = p.offsetClone(offset);
				output.add(new RailDraw(path, Side.UNKNOWN, position, side));
			}
		}
		return output;
	}
}
