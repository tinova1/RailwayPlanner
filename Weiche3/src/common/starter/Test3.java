package common.starter;

import common.vectorMath.objects3D.Point;

public class Test3 {
	private Test1 test1= new Test1();
	private Point p = test1.getP();
	public void main(String[] args) {
	test1.moveP();
	p.print();
	}
}
