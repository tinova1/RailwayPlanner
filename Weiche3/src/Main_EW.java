
import components.*;
import model.ew.ABWcreator;
import model.ew.EW_gerades;
import model.ew.PathTurnout;
import model.kr.Kr;
import model.kr.PathKr;
import vectorMath.RotDir;
import vectorMath.objects2D.Arc;
import vectorMath.objects2D.Path;
import vectorMath.objects3D.LineSeg;
import vectorMath.objects3D.Point;
import io.Export_obj;
import io.Export_svg;

public class Main_EW {

	public static void main(String[] args) {
		Timer ti = new Timer();
		Rail rail = new Rail();
		
		final Path p13 = new Path(new LineSeg(new Point(100,100),new Point(50,50)));
		p13.addArc(500, .5, RotDir.POS);
		final Path p24 = new Path(new LineSeg(new Point(-50,0),new Point(0,0)));
		p24.addArc(200, 1, RotDir.NEG);
		PathKr ibw = new PathKr(16.5,rail,p13,p24);
		
		
		//final Path pathLeft = new Path(new Arc(new Point(0,100),new Point(0,0),new Point(30,0),RotDir.POS));
		//pathLeft.addArc(100, .5, RotDir.NEG);
		//System.out.println(pathLeft.print());
		//final Path pathRight = new Path(new Arc(new Point(0,-200),new Point(0,0),new Point(100,0),RotDir.NEG));
		//pathRight.addLineSeg(50);
		//final Path tiePath = new Path(new LineSeg(new Point(0,0),new Point(100,0)));
		
		// Tie t = new Tie();
		// Railway rw = new Railway(new CSYS(), t, rail, 16.5);
		// EW ew = new EW(16.5, rail, 200, 40, 100, 0);
		// ABW ibw = new ABW(16.5, rail, 300., 300., .5, 2);
		//EW_gerades creator = new EW_gerades(16.5, rail, 200, 100, .5, 50);
		// ABWcreator creator = new ABWcreator(16.5, rail, 100, 300, .7, .5);
		//PathTurnout ibw = new PathTurnout(16.5,rail,pathLeft,pathRight);
		// ibw.rotate(Math.PI/8.);
		// Kr ibw = new Kr(16.5,rail,100,70,50,80,Math.toRadians(30));
		Export_obj.ausgabe("ausgabe", ibw);
		Export_svg svg = new Export_svg(ibw);
		svg.ausgabe();
		System.out.println("Fertig! " + ti.stopTime() + "s");
	}
}
