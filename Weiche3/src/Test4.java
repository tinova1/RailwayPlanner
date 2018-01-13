import java.util.ArrayList;
import java.util.List;

import components.Positions;
import components.RailDraw;
import components.RailStorage;
import vectorMath.RotDir;
import vectorMath.objects2D.Arc;
import vectorMath.objects2D.Circle;
import vectorMath.objects3D.Point;

public class Test4 {

	public static void main(String[] args) {
		RailStorage s = new RailStorage();
		s.add(new RailDraw(new Circle(new Point(),100)), 5, Positions.foot_inner);
	}

}
