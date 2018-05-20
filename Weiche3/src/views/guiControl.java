package views;

import common.components.Kleineisen;
import common.components.Rail;
import common.components.Tie;
import common.geometry.Canvas;
import common.io.Export_obj;
import common.io.Export_svg;
import common.railway.Railway;
import common.railway.RailwayPlain;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;
import dddEngine.DDDFrame;
import dddEngine.DDDPolygon;
import guiTransfer.CreateTurnout;

public class guiControl {
	public guiControl() {

	}

	public static void createButtonPressed(double gauge, double radiusLeft, double radiusRight, double radiusAppro,
			double angleLeft, double angleRight, double angleAppro, double strLeft, double strRight, double strAppro,
			double offsLeft, double offsRight, double foot, double head, double vFrog, double kFrog, double guide,
			double flange, int turnoutGeoIndex, int approIndex, boolean svg, boolean obj) {
		switch (turnoutGeoIndex) {
		case 0: // straight left-hand turnout
			angleRight = 0.;
			break;
		case 1: // straight right-hand turnout
			angleLeft = 0.;
			radiusRight *= -1.;
			break;
		case 2: // curved left-hand turnout
			offsLeft = 0.;
			break;
		case 3: // curved right-hand turnout
			offsRight = 0.;
			radiusRight *= -1.;
			radiusLeft *= -1.;
			break;
		case 4:// wye
			radiusRight *= -1.;
			break;
		}
		final Rail rail = new Rail(foot, head, vFrog, kFrog, guide, flange);
		Canvas canvas = new Canvas();
		canvas.addToRailwayList(CreateTurnout.pathTurnout(rail, gauge, radiusLeft, radiusRight, angleLeft, angleRight,
				strLeft, strRight));

		final Path approPath = new Path();
		switch (approIndex) {
		case 0: // straight
			approPath.add(new LineSeg(new Point(0, 0), new Point(strAppro, 0)));
			break;
		case 1: // left
			approPath.add(new Arc(new Point(0, -radiusAppro), radiusAppro, Math.PI / 2., Math.PI / 2. - angleAppro,
					RotDir.NEG));
			break;
		case 2: // right
			approPath.add(new Arc(new Point(0, radiusAppro), radiusAppro, -Math.PI / 2., -Math.PI / 2. + angleAppro,
					RotDir.POS));
			break;
		}
		Railway approach;
		approach = new RailwayPlain(gauge, rail, approPath);
		approach.rotate(Math.PI);
		canvas.addToRailwayList(approach);

		/*
		 * DDDENGINE
		 */

		DDDFrame frame = new DDDFrame();
		frame.setVisible(true);

		for (Railway r : canvas.getRailwayList()) {
			for (Tie t : r.getTieBand().getTieList()) {
				for (DDDPolygon p : t.getCube().getPolygons()) {
					frame.ScreenObject.addDDDPolygon(p);
				}
				for (Kleineisen k : t.getKleinList()) {
					for (DDDPolygon p : k.getPoly().getPolygons()) {
						frame.ScreenObject.addDDDPolygon(p);
					}
				}
			}
			for (Kleineisen k : r.getKleinList()) {
				for (DDDPolygon p : k.getPoly().getPolygons()) {
					frame.ScreenObject.addDDDPolygon(p);
				}
			}
		}

		// Date date = new Date();
		final String fileName = "ausgabe";// date.toString();
		if (obj) {
			Export_obj.ausgabe(fileName, canvas);
		}
		if (svg) {
			Export_svg s = new Export_svg(canvas);
			s.ausgabe(fileName);
		}

		/*
		 * DDDENGINE
		 */
		/*
		 * DDDFrame frame = new DDDFrame(); frame.setVisible(true);
		 * 
		 * for (Railway r : canvas.getRailwayList()) { for (Tie t :
		 * r.getTieBand().getTieList()) { for (DDDPolygon p : t.getCube().getPolygons())
		 * { frame.ScreenObject.addDDDPolygon(p); } for (Kleineisen k :
		 * t.getKleinList()) { for (DDDPolygon p : k.getPoly().getPolygons()) {
		 * frame.ScreenObject.addDDDPolygon(p); } } } for (Kleineisen k :
		 * r.getKleinList()) { for (DDDPolygon p : k.getPoly().getPolygons()) {
		 * frame.ScreenObject.addDDDPolygon(p); } } }
		 */
		System.out.println("Fertig " + fileName);
	}

}
