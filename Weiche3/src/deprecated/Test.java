
package deprecated;

import common.vectorMath.objects3D.Point;

public class Test {
	public static void main(String[] args) {
		Point p = new Point(4,2,0);
		Point center = new Point(4,1,0);
		p.rotate(center, new double[] {0,0,-Math.PI/2.});
		System.out.println(p.print());
	}
}
