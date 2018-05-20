package common.starter;

import common.components.Rail;
import common.geometry.Canvas;
import common.io.Export_obj;
import common.io.Export_svg;
import common.railway.kr.PathKr;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Main_EW {

	public static void main(String[] args) {
		Timer ti = new Timer();
		Rail rail = new Rail();

		final Path p13 = new Path(new LineSeg(new Point(100, 100), new Point(50, 50)));
		p13.addArc(200, .5, RotDir.POS);
		final Path p24 = new Path(new LineSeg(new Point(-50, 0), new Point(-20, 0)));
		p24.addArc(200, .7, RotDir.POS);
		PathKr ibw = new PathKr(16.5, rail, p13, p24);

		// final Path pathLeft = new Path(new Arc(new Point(0,100),new Point(0,0),new
		// Point(30,0),RotDir.POS));
		// pathLeft.addArc(100, .5, RotDir.NEG);
		// System.out.println(pathLeft.print());
		// final Path pathRight = new Path(new Arc(new Point(0,-200),new Point(0,0),new
		// Point(100,0),RotDir.NEG));
		// pathRight.addLineSeg(50);
		// final Path tiePath = new Path(new LineSeg(new Point(0,0),new Point(100,0)));

		// Tie t = new Tie();
		// Railway rw = new Railway(new CSYS(), t, rail, 16.5);
		// EW ew = new EW(16.5, rail, 200, 40, 100, 0);
		// ABW ibw = new ABW(16.5, rail, 300., 300., .5, 2);
		// EW_gerades creator = new EW_gerades(16.5, rail, 300, 100, .5, 50);
		// ABWcreator creator = new ABWcreator(16.5, rail, 100, 300, .7, .5);
		// PathTurnout ibw = creator.pathTurnout();
		// ibw.rotate(Math.PI/8.);
		// Kr ibw = new Kr(16.5,rail,100,70,50,80,Math.toRadians(30));
		Canvas canvas = new Canvas(ibw);
		Export_obj.ausgabe("ausgabe", canvas);
		Export_svg svg = new Export_svg(canvas);
		svg.ausgabe("ausgabe");
		System.out.println("Fertig! " + ti.stopTime() + "s");
	}
}
