package common.model.kr;

import java.util.List;

import common.components.KFrog;
import common.components.Positions;
import common.components.Rail;
import common.components.RailDraw;
import common.components.Side;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;

public class RailCompPathKr {
	private final PathKr kr;
	private final double gauge;
	private final List<RailDraw> railList;
	private final Rail r;

	// VFrog{13R, 13L, 24R, 24L} KFrog{13R, 13L, 24R, 24L}
	private Line[] trimLines = new Line[8];
	private Positions currentPos;

	private static final Side R = Side.RIGHT;
	private static final Side L = Side.LEFT;
	private static final boolean t = true;
	private static final boolean f = false;

	public RailCompPathKr(final PathKr kr) {
		this.kr = kr;
		this.gauge = this.kr.getGauge();
		this.railList = this.kr.getRailList();
		this.r = this.kr.getRail();
	}

	public enum Paths {
		_13, _31, _24, _42;
	}

	private Path rail(final int pathNo, final Side side, final boolean flange, final boolean dVersatzBool) {
		double versatz = this.gauge / 2.;
		double dVersatz = 0;
		if (dVersatzBool)
			dVersatz = this.kr.getRail().getOffset(currentPos);

		if (flange)
			versatz = this.gauge / 2. - this.r.getFlangeFreedom() + dVersatz;
		else
			versatz -= dVersatz;
		if (side == L)
			versatz *= -1.;

		return kr.getPath()[pathNo].offsetClone(versatz);
	}

	private Path _13R(final boolean flange, final boolean dVersatz) {
		return rail(0, R, flange, dVersatz);
	}

	private Path _13L(final boolean flange, final boolean dVersatz) {
		return rail(0, L, flange, dVersatz);
	}

	private Path _31R(final boolean flange, final boolean dVersatz) {
		return rail(1, R, flange, dVersatz);
	}

	private Path _24R(final boolean flange, final boolean dVersatz) {
		return rail(2, R, flange, dVersatz);
	}

	private Path _24L(final boolean flange, final boolean dVersatz) {
		return rail(2, L, flange, dVersatz);
	}

	private Path _42R(final boolean flange, final boolean dVersatz) {
		return rail(3, R, flange, dVersatz);
	}

	public void calcRails() {
		// final String[] positions = kr.getRail().getPositions();
		for (final Positions pos : Positions.values()) {
			this.currentPos = pos;
			this.calcRail(pos.getIndex() == 0);
		}
		final KFrog kLow = new KFrog(_24R(t, f), _31R(t, f), this.kr.getRail());
		final KFrog kHi = new KFrog(_42R(t, f), _13R(t, f), this.kr.getRail());
		this.railList.addAll(kLow.getRailList());
		this.railList.addAll(kHi.getRailList());
	}

	private void calcRail(final boolean first) {
		// main rails
		final Path[] _13R = { _13R(f, t), _13R(f, t), _13R(f, t) };
		final Path[] _13L = { _13L(f, t), _13L(f, t), _13L(f, t) };
		final Path[] _24R = { _24R(f, t), _24R(f, t), _24R(f, t) };
		final Path[] _24L = { _24L(f, t), _24L(f, t), _24L(f, t) };

		_13R[0].trim(_24L(f, t), f);
		_13R[1].trim(_24R(t, t), f);
		_13R[1].trim(_24L(t, f), t);
		_13R[2].trim(_24R(f, t), t);

		_13L[0].trim(_24L(f, t), f);
		_13L[1].trim(_24R(t, f), f);
		_13L[1].trim(_24L(t, t), t);
		_13L[2].trim(_24R(f, t), t);

		_24R[0].trim(_13R(f, t), f);
		_24R[1].trim(_13L(t, f), f);
		_24R[1].trim(_13R(t, t), t);
		_24R[2].trim(_13L(f, t), t);

		_24L[0].trim(_13R(f, t), f);
		_24L[1].trim(_13L(t, t), f);
		_24L[1].trim(_13R(t, f), t);
		_24L[2].trim(_13L(f, t), t);

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
		this.makeVFrogRight(first);
		this.makeVFrogLeft(first);

		// this.makeKFrogUpper(first);
		// this.makeKFrogLower(first);
	}

	private void makeVFrogRight(final boolean first) {
		final Path frog13L = _13L(t, t);
		final Path frog24L = _24L(t, t);
		frog13L.trim(_24L(f, t), f);
		frog24L.trim(_13L(f, t), t);
		if (first) {
			frog13L.trimAt(this.r.getVFrogLength(), t);
			frog24L.trimAt(this.r.getVFrogLength(), f);
			this.trimLines[1] = frog13L.orthoLineStart();
			this.trimLines[3] = frog24L.orthoLineEnd();
		} else {
			frog13L.trim(this.trimLines[1], t);
			frog24L.trim(this.trimLines[3], f);
		}
		this.railList.add(new RailDraw(frog13L));
		this.railList.add(new RailDraw(frog24L));
	}

	private void makeVFrogLeft(final boolean first) {
		final Path frog13R = _13R(t, t);
		final Path frog24R = _24R(t, t);
		frog13R.trim(_24R(f, t), t);
		frog24R.trim(_13R(f, t), f);
		if (first) {
			frog13R.trimAt(this.r.getVFrogLength(), f);
			frog24R.trimAt(this.r.getVFrogLength(), t);
			this.trimLines[0] = frog13R.orthoLineEnd();
			this.trimLines[2] = frog24R.orthoLineStart();
		} else {
			frog13R.trim(this.trimLines[0], f);
			frog24R.trim(this.trimLines[2], t);
		}
		this.railList.add(new RailDraw(frog13R));
		this.railList.add(new RailDraw(frog24R));
	}

	/*
	 * private void makeKFrogUpper(final boolean first) { final Path frog13R =
	 * _13R(t, t); final Path frog24L = _24L(t, t); frog13R.trim(_24L(t, t), t);
	 * frog24L.trim(_13R(t, t), t); if (first) {
	 * frog13R.trimAt(this.r.getKFrogLength(), f);
	 * frog24L.trimAt(this.r.getKFrogLength(), f); this.trimLines[4] =
	 * frog13R.orthoLineEnd(); this.trimLines[7] = frog24L.orthoLineEnd(); } else {
	 * frog13R.trim(this.trimLines[4], f); frog24L.trim(this.trimLines[7], f); }
	 * this.railList.add(new RailDraw(frog13R)); this.railList.add(new
	 * RailDraw(frog24L)); }
	 * 
	 * private void makeKFrogLower(final boolean first) {
	 * 
	 * final Path frog13L = _13L(t, t); final Path frog24R = _24R(t, t);
	 * frog13L.trim(_24R(t, t), f); frog24R.trim(_13L(t, t), f); if (first) {
	 * frog13L.trimAt(this.r.getKFrogLength(), t);
	 * frog24R.trimAt(this.r.getKFrogLength(), t); this.trimLines[5] =
	 * frog13L.orthoLineStart(); this.trimLines[6] = frog24R.orthoLineStart(); }
	 * else { frog13L.trim(this.trimLines[5], t); frog24R.trim(this.trimLines[6],
	 * t); } this.railList.add(new RailDraw(frog13L)); this.railList.add(new
	 * RailDraw(frog24R)); }
	 */
}
