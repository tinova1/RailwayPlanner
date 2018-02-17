package common.computers.rail;

import java.util.ArrayList;
import java.util.List;

import common.components.Rail;
import common.components.RailDraw;
import common.vectorMath.objects2D.Path;
import utils.Side;

public abstract class RailCompPlain {

	public static List<RailDraw> path(final Path p, final double gauge, final Rail r, final Side side) {
		List<RailDraw> output = new ArrayList<>();
		if (side == Side.BOTH) {
			output.addAll(path(p, gauge, r, Side.LEFT));
			output.addAll(path(p, gauge, r, Side.RIGHT));
		} else {
			double[] offsets = r.getOffsets();
			// String[] positions = r.getPositions();
			for (int i = 0; i < offsets.length; i++) {
				double offset = offsets[i] + gauge / 2.;
				if (side == Side.LEFT)
					offset *= -1.;

				final Path path = p.offsetClone(offset);
				output.add(new RailDraw(path));
			}
		}
		return output;
	}
}
