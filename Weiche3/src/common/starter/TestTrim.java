package common.starter;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class TestTrim {
	public static void main(String[] args) {
		LineSeg l = new LineSeg(new Point(0,0),new Point(1,1));
		LineSeg m = new LineSeg(new Point(1,0),new Point(0,1));
		Path p = new Path(l);
		Path q = new Path(m);
		
		p.trimAt(.707, false);
		System.out.println(p.print());
	}
}
