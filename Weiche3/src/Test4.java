import vectorMath.RotDir;
import vectorMath.objects2D.Arc;
import vectorMath.objects3D.Point;

public class Test4 {

	public static void main(String[] args) {
		final double st = 3./4.*Math.PI;
		final double en = Math.PI/4.;
		Arc arc = new Arc(new Point(0, 0), 1, st,en, RotDir.NEG);
		for (double a = -Math.PI*2.; a < Math.PI * 4.; a += Math.PI / 8.) {
			Point p = new Point(1, 0);
			p.rotate(a);
			System.out.println(arc.PointOnArc(p) + " " + a / Math.PI + "pi");
		}
	}

}
