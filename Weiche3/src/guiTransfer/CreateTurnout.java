package guiTransfer;

import common.components.Rail;
import common.railway.ew.PathTurnout;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Arc;
import common.vectorMath.objects2D.Path;
import common.vectorMath.objects3D.Point;

public abstract class CreateTurnout {
	public static PathTurnout pathTurnout(final Rail rail, final double gauge,
			final double radiusLeft, final double radiusRight, final double angleLeft,
			final double angleRight, final double strLeft, final double strRight) {

		final Path pathLeft = new Path();
		if (radiusLeft >= 0.) {
			pathLeft.add(new Arc(new Point(0, radiusLeft), radiusLeft, -Math.PI / 2., -Math.PI / 2. + angleLeft,
					RotDir.POS));
		} else {
			pathLeft.add(
					new Arc(new Point(0, radiusLeft), -radiusLeft, Math.PI / 2., Math.PI / 2. + angleLeft, RotDir.NEG));
		}
		pathLeft.addLineSeg(strLeft);
		final Path pathRight = new Path();
		if (radiusRight >= 0.) {
			pathRight.add(new Arc(new Point(0, radiusRight), radiusRight, -Math.PI / 2., -Math.PI / 2. + angleRight,
					RotDir.POS));
		} else {
			pathRight.add(new Arc(new Point(0, radiusRight), -radiusRight, Math.PI / 2., Math.PI / 2. - angleRight,
					RotDir.NEG));
		}
		pathRight.addLineSeg(strRight);

		return new PathTurnout(gauge, rail, pathLeft, pathRight);
	}
}
