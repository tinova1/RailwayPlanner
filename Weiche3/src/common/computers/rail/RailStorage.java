package common.computers.rail;

import java.util.ArrayList;
import java.util.List;

import common.components.RailDraw;
import common.vectorMath.objects2D.Curve;
import utils.Positions;

public class RailStorage {
	private List<RailDraw[]> railList = new ArrayList<>();

	final static int positions = Positions.values().length;

	public RailStorage() {

	}

	public void add(final RailDraw r, final int listNo, final Positions position) {
		for (int i = this.railList.size(); i <= listNo; i++) {
			this.railList.add(new RailDraw[positions]);
		}

		this.railList.get(listNo)[position.getIndex()] = r;
		System.out.println(this.railList.size());
	}

	public Curve getOneCurve(final int listNo, final Positions pos) {
		try {
			return this.railList.get(listNo)[pos.getIndex()].getCurve();
		} catch (IndexOutOfBoundsException e) {
			e.getLocalizedMessage();
			return null;
		}
	}

	public List<RailDraw> getOne(final Positions pos) {
		final List<RailDraw> output = new ArrayList<>();
		for (RailDraw[] array : this.railList) {
			final RailDraw r = array[pos.getIndex()];
			if (r != null) {
				output.add(r);
			}
		}
		return output;
	}

	public List<RailDraw> getAll() {
		final List<RailDraw> output = new ArrayList<>();
		for (Positions pos : Positions.values()) {
			output.addAll(this.getOne(pos));
		}
		return output;
	}
}
