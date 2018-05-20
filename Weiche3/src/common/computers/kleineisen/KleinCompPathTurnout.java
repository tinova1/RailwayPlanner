package common.computers.kleineisen;

import java.util.ArrayList;
import java.util.List;

import common.components.Kleineisen;
import common.components.RailDraw;
import common.components.Tie;
import common.railway.Railway;
import common.vectorMath.Orientation;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Axis;
import common.vectorMath.objects3D.Cube;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;
import utils.Side;

public class KleinCompPathTurnout {

	public static List<Kleineisen> compute(final Railway w, boolean collisionDetection) {
		//collisionDetection = false;
		List<Kleineisen> output = new ArrayList<>();

		ArrayList<Tie> tie_list = w.getTieBand().getTieList();
		List<RailDraw> rail_list = w.getRailList();

		// make a collision list with only foot rails that is used for collision
		// detection
		final List<RailDraw> collisionList = new ArrayList<>();
		for (RailDraw r : w.getRailList()) {
			if (r.getPosition().isFoot()) {
				collisionList.add(r);
			}
		}

		for (int i = 0; i < rail_list.size(); i++) {
			RailDraw rail = rail_list.get(i);
			if (rail.getPosition().isFoot()) {
				for (int j = 0; j < tie_list.size(); j++) {
					Tie tie = tie_list.get(j);
					final Path railPath = new Path(rail.getCurve());
					Point[] inter = tie.getLineSeg().intersection(railPath);
					if (inter.length == 0)
						continue;
					Kleineisen newklein = new Kleineisen(rail, tie, "default");
					//some crazy logic to rotate Kleineisen correctly
					if (rail.getPosition().isOuter() == (rail.getRailSide() == Side.RIGHT)) {
						newklein.getPoly().rotateZ(Math.PI);
					}

					final Path restRail = railPath.clone();
					restRail.trim(tie.getLineSeg(), false);
					double rotAngle = restRail.orthoLineEnd().getAngle();
					rotAngle -= Math.PI / 2.;
					rotAngle %= Math.PI;
					newklein.getPoly().getCSYS().rotate(new double[] { 0, 0, rotAngle });
					newklein.getPoly().getCSYS().move(railPath.getStartPoint());
					newklein.getPoly().getCSYS().moveAlong(railPath, restRail.getLength(), false);
					/*
					 * if (rail.getCurve() instanceof Arc) { Point arc_center = ((Arc)
					 * rail.getCurve()).getC(); double arc_angle = Math.atan2(p.getY() -
					 * arc_center.getY(), p.getX() - arc_center.getX());
					 * newklein.getPoly().rotateZ(arc_angle - Math.PI / 2.); } if
					 * (rail.getLeftRight().equalsIgnoreCase("right"))
					 * newklein.getPoly().rotateZ(Math.PI);
					 */

					/*
					 * if klein does not collide with anything or collision detection is deactivated
					 * add klein to kleinList of Tie
					 */
					if (!collisionDetection || !kleinCollidesAnything(newklein, collisionList)) {
						tie.getKleinList().add(newklein);
					}
				}
			}
		}
		return output;
	}

	/*
	 * public void berechnen2() { ArrayList<Tie> tie_list =
	 * w.getTieBand().getTieList(); for (int i = 0; i < tie_list.size(); i++) {
	 * berechnen(tie_list.get(i)); } }
	 * 
	 * public void berechnen(Tie tie) { List<RailDraw> rail_list = w.getRailList();
	 * 
	 * for (int i = 0; i < rail_list.size(); i++) { RailDraw rail =
	 * rail_list.get(i); if (rail.getPosition().startsWith("foot"))
	 * 
	 * if (match(tie, rail)) {
	 * 
	 * Point[] inter = tie.getLineSeg().intersection(rail.getCurve()); if
	 * (inter.length == 0) continue; Point p = inter[0];
	 * 
	 * Kleineisen newklein = new Kleineisen(rail, tie, "default");
	 * 
	 * if (rail.getCurve() instanceof Arc) { Point arc_center = ((Arc)
	 * rail.getCurve()).getC(); double arc_angle = Math.atan2(p.getY() -
	 * arc_center.getY(), p.getX() - arc_center.getX());
	 * newklein.getPoly().rotateZ(arc_angle - Math.PI / 2.); } if
	 * (rail.getLeftRight().equalsIgnoreCase("right"))
	 * newklein.getPoly().rotateZ(Math.PI);
	 * 
	 * newklein.getPoly().move(p); tie.getKleinList().add(newklein); }
	 * 
	 * } collisionDetection(tie.getKleinList()); }
	 */
	private static boolean kleinCollidesAnything(final Kleineisen klein, final List<RailDraw> railList) {
		return kleinCollidesKlein(klein) || kleinCollidesCurves(klein, railList);
	}

	/*
	 * private void collisionDetection(ArrayList<Kleineisen> input) { for (int i =
	 * 0; i < input.size(); i++) { if (input.get(i).getActive() == false) {
	 * continue; } if (kleinCollidesCurves(input.get(i), w.getRailList())) {
	 * removeIfPossible(input.get(i)); continue; } // Klein collides with other
	 * Klein for (int j = 0; j < input.size(); j++) { if (input.get(j).getActive()
	 * == false) { continue; } if (i == j) { continue; } if
	 * (CollisionDetection.cubeInCube(input.get(i).getBoundingBox(),
	 * input.get(j).getBoundingBox())) { removeOne(input.get(i), input.get(j));
	 * break; } } } }
	 * 
	 * /* private boolean match(Tie tie, RailDraw rail) { String tie_type =
	 * tie.getType(); String rail_type = rail.getRailSide().getString();
	 * 
	 * if (tie_type.equalsIgnoreCase("turnout")) { return true; } else if
	 * (rail_type.equalsIgnoreCase(tie_type)) { return true; } else if
	 * (rail_type.equalsIgnoreCase("left") && tie_type.equalsIgnoreCase("approach"))
	 * { return true; } else { return false; } }
	 * 
	 * private void removeOne(Kleineisen a, Kleineisen b) { if (isOuter(a)) {
	 * b.setActive(false); } else if (isOuter(b)) { a.setActive(false); // } else if
	 * (a.getRail().getRailSide()==a.getRail().getLeftRight()) { //
	 * b.setActive(false); } else { a.setActive(false); } }
	 * 
	 * private void removeIfPossible(Kleineisen a) { if (!isOuter(a)) {
	 * a.setActive(false); } }
	 * 
	 * private boolean isOuter(Kleineisen a) { return a.getRail().isOuter(); }
	 */
	private static boolean kleinCollidesKlein(final Kleineisen klein) {
		final Tie tie = klein.getTie();
		for (Kleineisen object : tie.getKleinList()) {
			final Cube bb1 = klein.getBoundingBox(); // first Bounding Box
			final Cube bb2 = object.getBoundingBox(); // second Bounding Box
			if (CollisionDetection.cubeInCube(bb1, bb2)) {
				return true;
			}
		}
		return false;
	}

	private static boolean kleinCollidesCurves(final Kleineisen klein, final List<RailDraw> railList) {
		final Cube boundingBox = klein.getBoundingBox();
		final LineSeg[] rect = boundingBox.project(Axis.Z, Orientation.GLOBAL);
		int count = railList.size();
		for (int i = 0; i < count; i++) {
			// continue if current rail is rail that belongs to Klein
			if (klein.getRail() == railList.get(i)) {
				continue;
			}
			if (CollisionDetection.polyInCurve(rect, railList.get(i).getCurve())) {
				return true;
			}
		}
		return false;
	}

}