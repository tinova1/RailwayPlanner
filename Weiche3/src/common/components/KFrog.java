package common.components;

import java.util.ArrayList;
import java.util.List;

import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Line;

public class KFrog {
	private static final boolean f = false;
	private static final boolean t = true;
	
	private final Rail r;
	private final Path[] paths = new Path[2];
	private Line[] trimLines = new Line[2];
	private Positions currentPos;
	private final List<RailDraw> railList = new ArrayList<RailDraw>();

	public KFrog(final Path path1, final Path path2, final Rail r) {
		this.r = r;
		this.paths[1] = path1;
		this.paths[0] = path2;
		this.makeKFrog();
	}

	public List<RailDraw> getRailList() {
		return this.railList;
	}

	private void makeKFrog() {
		for (final Positions pos : Positions.values()) {
		//Positions pos = Positions.head_inner;
			this.currentPos = pos;
			this.createRails(pos.getIndex() == 0);
		}
	}

	private Path[] rails() {
		final double offset = this.r.getOffset(currentPos);
		final Path[] rails = new Path[2];
		for (int i = 0; i < rails.length; i++) {
			rails[i] = this.paths[i].clone();
			rails[i].offset(offset);
		}
		return rails;
	}

	private void createRails(final boolean first) {
		final Path path1 = this.rails()[0];
		final Path path2 = this.rails()[1];
		path1.trim(this.rails()[1], t);
		path2.trim(this.rails()[0], f);
		if (first) {
			path1.trimAt(this.r.getKFrogLength(), f);
			path2.trimAt(this.r.getKFrogLength(), t);
			this.trimLines[0] = path1.orthoLineEnd();
			this.trimLines[1] = path2.orthoLineStart();
		} else {
			path1.trim(this.trimLines[0], f);
			path2.trim(this.trimLines[1], t);
		}
		path1.add(path2);
		this.railList.add(new RailDraw(path1));
	}

}
