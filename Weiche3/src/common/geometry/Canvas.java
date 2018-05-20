package common.geometry;

import java.util.ArrayList;
import java.util.List;

import common.railway.Railway;

/*
 * A canvas where all Railway elements are stored, so that all can be exported to svg/obj
 */

public class Canvas {
	private List<Railway> railwayList = new ArrayList<>();

	public Canvas() {
	}

	public Canvas(Railway w) {
		this.railwayList.add(w);
	}

	public List<Railway> getRailwayList() {
		return this.railwayList;
	}

	public void addToRailwayList(final Railway r) {
		this.railwayList.add(r);
	}

}
