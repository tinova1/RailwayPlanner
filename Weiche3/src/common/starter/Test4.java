package common.starter;
import java.util.ArrayList;
import java.util.List;

import common.components.Positions;
import common.components.RailDraw;
import common.components.RailStorage;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Circle;
import common.vectorMath.objects3D.Point;

public class Test4 {

	public static void main(String[] args) {
		RailStorage s = new RailStorage();
		s.add(new RailDraw(new Circle(new Point(),100)), 5, Positions.foot_inner);
	}

}
