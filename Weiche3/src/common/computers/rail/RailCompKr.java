package common.computers.rail;

import java.util.List;

import common.components.Rail;
import common.components.RailDraw;
import common.railway.kr.Kr;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;
import utils.Side;

public class RailCompKr {
	private final Kr kr;
	private final double gauge;
	private final List<RailDraw> railList;
	private final Rail r;

	// {13R, 13L, 24R, 24L}
	private Line[] trimLines = new Line[4];
	private double offset = 0.;

	private static final Side R = Side.RIGHT;
	private static final Side L = Side.LEFT;

	public RailCompKr(final Kr kr) {
		this.kr = kr;
		this.gauge = this.kr.getGauge();
		this.railList = this.kr.getRailList();
		this.r = this.kr.getRail();
	}

	private Path rail(final int from, final int to, final Side side, final boolean flange) {
		double offset = this.gauge / 2.;
		if (flange)
			offset -= this.r.getFlangeFreedom();
		else
			offset -= this.offset;
		if (side == Side.LEFT)
			offset *= -1.;

		return kr.path(from, to).offsetClone(offset);
	}

	private Path _13R(final boolean flange) {
		return rail(1, 3, R, flange);
	}

	private Path _13L(final boolean flange) {
		return rail(1, 3, L, flange);
	}

	private Path _24R(final boolean flange) {
		return rail(2, 4, R, flange);
	}

	private Path _24L(final boolean flange) {
		return rail(2, 4, L, flange);
	}

	public void calcRails() {
		// final String[] positions = kr.getRail().getPositions();
		final double[] offsets = kr.getRail().getOffsets();
		for (int i = 0; i < offsets.length; i++) {
			this.offset = offsets[i];
			this.calcRail();
		}
	}

	private void calcRail() {
		// main rails
		final Path[] _13R = { _13R(false), _13R(false), _13R(false) };
		final Path[] _13L = { _13L(false), _13L(false), _13L(false) };
		final Path[] _24R = { _24R(false), _24R(false), _24R(false) };
		final Path[] _24L = { _24L(false), _24L(false), _24L(false) };

		_13R[0].trim(_24L(false), false);
		_13R[1].trim(_24R(true), false);
		_13R[1].trim(_24L(true), true);
		_13R[2].trim(_24R(false), true);

		_13L[0].trim(_24L(false), false);
		_13L[1].trim(_24R(true), false);
		_13L[1].trim(_24L(true), true);
		_13L[2].trim(_24R(false), true);

		_24R[0].trim(_13R(false), false);
		_24R[1].trim(_13L(true), false);
		_24R[1].trim(_13R(true), true);
		_24R[2].trim(_13L(false), true);

		_24L[0].trim(_13R(false), false);
		_24L[1].trim(_13L(true), false);
		_24L[1].trim(_13R(true), true);
		_24L[2].trim(_13L(false), true);

		for (Path p : _13R) {
			this.railList.add(new RailDraw(p));
		}
		for (Path p : _24R) {
			this.railList.add(new RailDraw(p));
		}
		for (Path p : _13L) {
			this.railList.add(new RailDraw(p));
		}
		for (Path p : _24L) {
			this.railList.add(new RailDraw(p));
		}

		// frog rails
		final Path frog13R = _13R(true);
		final Path frog13L = _13L(true);
		final Path frog24R = _24R(true);
		final Path frog24L = _24L(true);

		frog13R.trim(_24R(false), true);
		frog13R.trimAt(this.r.getVFrogLength(), false);
		frog13L.trim(_24L(false), false);
		frog13L.trimAt(this.r.getVFrogLength(), true);
		frog24R.trim(_13R(false), false);
		frog24R.trimAt(this.r.getVFrogLength(), true);
		frog24L.trim(_13L(false), true);
		frog24L.trimAt(this.r.getVFrogLength(), false);

		this.railList.add(new RailDraw(frog13R));
		this.railList.add(new RailDraw(frog13L));
		this.railList.add(new RailDraw(frog24R));
		this.railList.add(new RailDraw(frog24L));

		this.trimLines[0] = frog13R.orthoLineEnd();
		this.trimLines[1] = frog13L.orthoLineStart();
		this.trimLines[2] = frog24R.orthoLineStart();
		this.trimLines[3] = frog24L.orthoLineEnd();
	}
}
