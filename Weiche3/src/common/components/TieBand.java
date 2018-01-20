package components;

import java.util.ArrayList;

import vectorMath.objects3D.Point;

public class TieBand {
	private ArrayList<Tie> tieList = new ArrayList<>();

	public TieBand() {
	}

	public TieBand(ArrayList<Tie> tieList) {
		this.tieList = tieList;
	}

	public ArrayList<Tie> getTieList() {
		return this.tieList;
	}

	public void add(Tie t) {
		this.tieList.add(t);
	}

	public void add(ArrayList<Tie> t) {
		this.tieList.addAll(t);
	}

	public void add(TieBand t) {
		this.add(t.getTieList());
	}

	public void move(final Point dp) {
		for (Tie i : tieList) {
			i.getCube().move(dp);
		}
	}

	public void rotate(final Point center, final double dRot) {
		for (Tie i : tieList) {
			i.getCube().getCSYS().rotate(center, new double[] { 0, 0, dRot });
		}
	}
}
