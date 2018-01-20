package common.starter;
import common.vectorMath.objects3D.Point;

public class Test1 {
	public Test1() {
	}

	private Point p = new Point();

	public void moveP() {
		p.move(1, 0, 0);
	}

	public Point getP() {
		return p;
	}

}
