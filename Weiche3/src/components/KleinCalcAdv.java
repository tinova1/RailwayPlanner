package components;

import components.Kleineisen;
import geometry.*;
import model.ew.PathTurnout;
import vectorMath.Orientation;
import vectorMath.objects2D.Arc;
import vectorMath.objects3D.Axis;
import vectorMath.objects3D.Cube;
import vectorMath.objects3D.LineSeg;
import vectorMath.objects3D.Point;

import java.util.ArrayList;

public class KleinCalcAdv {

	private PathTurnout w;

	public KleinCalcAdv(PathTurnout w) {
		this.w = w;
	}

	public ArrayList<Kleineisen> berechnen() {
		ArrayList<Tie> tie_list = w.getTieBand().getTieList();
		ArrayList<RailDraw> rail_list = w.getRailList();

		ArrayList<Kleineisen> ausgabe = new ArrayList<Kleineisen>();

		for (int i = 0; i < rail_list.size(); i++) {
			RailDraw rail = rail_list.get(i);
			if (rail.getPosition().startsWith("foot"))
				for (int j = 0; j < tie_list.size(); j++) {
					Tie tie = tie_list.get(j);

					if (!match(tie, rail))
						continue;
					Point[] inter = tie.getLine().intersection(rail.getCurve());
					if (inter.length == 0)
						continue;
					Point p = inter[0];

					Kleineisen newklein = new Kleineisen(rail, tie, "default");

					if (rail.getCurve() instanceof Arc) {
						Point arc_center = ((Arc) rail.getCurve()).getC();
						double arc_angle = Math.atan2(p.getY() - arc_center.getY(), p.getX() - arc_center.getX());
						newklein.getPoly().rotateZ(arc_angle - Math.PI / 2.);
					}
					if (rail.getLeftRight().equalsIgnoreCase("right"))
						newklein.getPoly().rotateZ(Math.PI);

					newklein.getPoly().move(p);
					ausgabe.add(newklein);
				}
		}
		collisionDetection(ausgabe);
		return ausgabe;
	}

	public void berechnen2() {
		ArrayList<Tie> tie_list = w.getTieBand().getTieList();
		for (int i = 0; i < tie_list.size(); i++) {
			berechnen(tie_list.get(i));
		}
	}

	public void berechnen(Tie tie) {
		ArrayList<RailDraw> rail_list = w.getRailList();

		for (int i = 0; i < rail_list.size(); i++) {
			RailDraw rail = rail_list.get(i);
			if (rail.getPosition().startsWith("foot"))

				if (match(tie, rail)) {

					Point[] inter = tie.getLine().intersection(rail.getCurve());
					if (inter.length == 0)
						continue;
					Point p = inter[0];

					Kleineisen newklein = new Kleineisen(rail, tie, "default");

					if (rail.getCurve() instanceof Arc) {
						Point arc_center = ((Arc) rail.getCurve()).getC();
						double arc_angle = Math.atan2(p.getY() - arc_center.getY(), p.getX() - arc_center.getX());
						newklein.getPoly().rotateZ(arc_angle - Math.PI / 2.);
					}
					if (rail.getLeftRight().equalsIgnoreCase("right"))
						newklein.getPoly().rotateZ(Math.PI);

					newklein.getPoly().move(p);
					tie.getKleinList().add(newklein);
				}

		}
		collisionDetection(tie.getKleinList());
	}

	private void collisionDetection(ArrayList<Kleineisen> input) {
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).getActive() == false) {
				continue;
			}
			if (kleinCollidesCurves(input.get(i), w.getRailList())) {
				removeIfPossible(input.get(i));
				continue;
			}
			// Klein collides with other Klein
			for (int j = 0; j < input.size(); j++) {
				if (input.get(j).getActive() == false) {
					continue;
				}
				if (i == j) {
					continue;
				}
				if (CollisionDetection.cubeInCube(input.get(i).getBoundingBox(), input.get(j).getBoundingBox())) {
					removeOne(input.get(i), input.get(j));
					break;
				}
			}
		}
	}

	private boolean match(Tie tie, RailDraw rail) {
		String tie_type = tie.getType();
		String rail_type = rail.getRailSide().getString();

		if (tie_type.equalsIgnoreCase("turnout")) {
			return true;
		} else if (rail_type.equalsIgnoreCase(tie_type)) {
			return true;
		} else if (rail_type.equalsIgnoreCase("left") && tie_type.equalsIgnoreCase("approach")) {
			return true;
		} else {
			return false;
		}
	}

	private void removeOne(Kleineisen a, Kleineisen b) {
		if (isOuter(a)) {
			b.setActive(false);
		} else if (isOuter(b)) {
			a.setActive(false);
			// } else if (a.getRail().getRailSide()==a.getRail().getLeftRight()) {
			// b.setActive(false);
		} else {
			a.setActive(false);
		}
	}

	private void removeIfPossible(Kleineisen a) {
		if (!isOuter(a)) {
			a.setActive(false);
		}
	}

	private boolean isOuter(Kleineisen a) {
		return a.getRail().isOuter();
	}

	public static boolean kleinCollidesCurves(Kleineisen a, ArrayList<RailDraw> r) {
		for (int i = 0; i < r.size(); i++) {
			if (a.getRail().getName().equalsIgnoreCase(r.get(i).getName()))
				continue;
			Cube boundingBox = a.getBoundingBox();
			LineSeg[] rect = boundingBox.project(Axis.Z, Orientation.GLOBAL);
			if (CollisionDetection.polyInCurve(rect, r.get(i).getCurve())) {
				return true;
			}
		}
		return false;
	}
}