package common.vectorMath.objects2D;

import java.util.ArrayList;

import common.svgCreator.Tag;
import common.vectorMath.MathUtils;
import common.vectorMath.RotDir;
import common.vectorMath.objects2D.Curve;
import common.vectorMath.objects3D.Line;
import common.vectorMath.objects3D.LineSeg;
import common.vectorMath.objects3D.Point;

public class Path extends Curve {
	private ArrayList<Curve> curves = new ArrayList<>();

	public Path() {

	}

	public Path(Curve c) {
		if (c instanceof Path) {
			curves.addAll(((Path) c).getCurves());
		} else {
			curves.add(c);
		}
	}

	public void add(Curve curve) {
		if (!(curve instanceof LineSeg || curve instanceof Arc)) {
			System.out.println("Achtung: fügt unzulässige Kurve in Path ein: " + curve.getClass());
		}
		this.curves.add(curve);
	}

	public void add(ArrayList<Curve> c) {
		this.curves.addAll(c);
	}

	public void add(Path p) {
		this.add(p.getCurves());
	}

	public void addLineSeg(final double length) {
		final Curve last = this.getLastCurve();
		double angle;
		final Point endPoint = last.getEndPoint();
		if (last instanceof Line) {
			angle = ((Line) last).getAngle();
		} else {
			angle = ((Arc) last).getEnd() - Math.PI / 2.;
			if (((Arc) last).getRotDir() == RotDir.POS) {
				angle += Math.PI;
			}
		}
		this.add(new LineSeg(endPoint, angle, length));
	}

	public void addArc(final double radius, final double arcAngle, final RotDir rotDir) {
		final Curve last = this.getLastCurve();
		final Point startPoint = last.getEndPoint();
		final Point endPoint = startPoint.clone();
		final Point center = startPoint.clone();

		if (last instanceof LineSeg) {
			final double lastAngle = ((LineSeg) last).getAngle();
			if (rotDir == RotDir.POS) {
				center.move(radius, lastAngle + Math.PI / 2.);
			} else {
				center.move(radius, lastAngle - Math.PI / 2.);
			}
		} else {
			final double lastEnd = ((Arc) last).getEnd();
			final RotDir lastRotDir = ((Arc) last).getRotDir();
			if (lastRotDir == RotDir.POS) {
				if (rotDir == RotDir.POS) {
					center.move(-radius, lastEnd);
				} else {
					center.move(radius, lastEnd);
				}
			} else {
				if (rotDir == RotDir.POS) {
					center.move(radius, lastEnd);
				} else {
					center.move(-radius, lastEnd);
				}
			}
		}
		if (rotDir == RotDir.POS) {
			endPoint.rotate(center, arcAngle);
		} else {
			endPoint.rotate(center, -arcAngle);
		}
		this.add(new Arc(center, startPoint, endPoint, rotDir));
	}

	public ArrayList<Curve> getCurves() {
		return this.curves;
	}

	public Path getReverse() {
		final Path output = this.clone();
		output.reverse();
		return output;
	}

	public void reverse() {
		final ArrayList<Curve> output = new ArrayList<>();
		for (int i = this.curves.size() - 1; i >= 0; i--) {
			output.add(this.curves.get(i).getReverse());
		}
		this.curves = output;
	}

	public Tag export_svg() {
		if (this.curves.size() == 0)
			return null;

		final Tag tag = new Tag("path", true);
		String dEntryVal = "";

		for (int i = 0; i < curves.size(); i++) {
			final Curve c = curves.get(i);
			dEntryVal += "M";
			dEntryVal += getStartCoords(c);
			if (c instanceof Arc) {
				final double r = ((Arc) c).getR();
				final boolean large_arc_flag = ((Arc) c).getArcAngle() > Math.PI;
				final boolean sweep_flag = ((Arc) c).getRotDir() == RotDir.POS;
				dEntryVal += "A "//
						+ r + " " //
						+ r + " "//
						+ boolToInt(false) + " " //
						+ boolToInt(large_arc_flag) + " "//
						+ boolToInt(sweep_flag);
			} else {
				dEntryVal += "L";
			}
			dEntryVal += getEndCoords(c);
		}

		tag.addEntry("d", dEntryVal);
		return tag;
	}

	private static String getStartCoords(Curve c) {
		final double x = c.startPoint().getX();
		final double y = c.startPoint().getY();
		return " " + x + " " + y + " ";
	}

	private static String getEndCoords(Curve c) {
		final double x = c.getEndPoint().getX();
		final double y = c.getEndPoint().getY();
		return " " + x + " " + y + " ";
	}

	private static int boolToInt(final boolean b) {
		if (b)
			return 1;
		else
			return 0;
	}

	public double getLength() {
		double output = 0;
		for (Curve i : curves) {
			output += i.getLength();
		}
		return output;
	}

	public Path clone() {
		Path output = new Path();
		for (Curve i : curves) {
			output.add(i.clone());
		}
		return output;
	}

	public Path offsetClone(final double offset) {
		Path path = this.clone();
		path.offset(offset);
		return path;
	}

	public void offset(final double offset) {
		for (Curve i : this.curves) {
			if (i instanceof Arc) {
				Arc arc = (Arc) i;
				if (arc.getRotDir() == RotDir.NEG) {
					arc.setR(arc.getR() - offset);
				} else {
					arc.setR(arc.getR() + offset);
				}
			} else if (i instanceof Line) {
				Line line = (Line) i;
				// normal points to the right
				line.move(offset, line.getAngle() - Math.PI / 2.);
			}
		}
	}

	public void rotate(Point center, double drot) {
		for (Curve i : curves) {
			i.rotate(center, drot);
		}
	}

	public void move(Point dp) {
		for (Curve i : curves) {
			i.move(dp);
		}
	}

	public void extrapolate(final double atStart, final double atEnd) {
		final Curve first = this.getFirstCurve();
		if (first instanceof LineSeg) {
			first.startPoint().move(-atStart, ((LineSeg) first).getAngle());
		} else if (first instanceof Arc) {
			final Point center = ((Arc) first).getC();
			final double radius = ((Arc) first).getR();
			final RotDir rotDir = ((Arc) first).getRotDir();
			double rotAngle = MathUtils.toAngle(atStart, radius);
			if (rotDir == RotDir.POS)
				rotAngle *= -1.;
			final Point newStartPoint = first.startPoint();
			newStartPoint.rotate(center, rotAngle);
			first.setStartPoint(newStartPoint);
		}
		final Curve last = this.getLastCurve();
		if (last instanceof LineSeg) {
			last.getEndPoint().move(atEnd, ((LineSeg) last).getAngle());
		} else if (last instanceof Arc) {
			final Point center = ((Arc) last).getC();
			final double radius = ((Arc) last).getR();
			final RotDir rotDir = ((Arc) last).getRotDir();
			double rotAngle = MathUtils.toAngle(atEnd, radius);
			if (rotDir == RotDir.NEG)
				rotAngle *= -1.;
			final Point newEndPoint = first.getEndPoint();
			newEndPoint.rotate(center, rotAngle);
			last.setEndPoint(newEndPoint);
		}
	}

	public Point[] intersection(Curve c) {
		final Path p = new Path(c);
		ArrayList<Point> output = new ArrayList<>();
		for (Curve c1 : this.curves) {
			for (Curve c2 : p.getCurves()) {
				final Point[] intersection = c1.intersection(c2);
				if (intersection.length > 0) {
					for (Point point : intersection) {
						output.add(point);
					}
				}
			}
		}
		return output.toArray(new Point[output.size()]);
	}

	public void trim(final Curve c, final boolean reverse) {
		if (!reverse) {
			for (int a = 0; a < this.curves.size(); a++) {
				final Curve i = this.curves.get(a);
				final Point[] inters = i.intersection(c);
				if (inters.length > 0) {
					i.setEndPoint(inters[0]);
					this.curves = new ArrayList<Curve>(this.curves.subList(0, a + 1));
					break;
				}
			}
		} else {
			for (int a = this.curves.size() - 1; a >= 0; a--) {
				final Curve i = this.curves.get(a);
				final Point[] inters = i.intersection(c);
				if (inters.length > 0) {
					i.setStartPoint(inters[0]);
					this.curves = new ArrayList<Curve>(this.curves.subList(a, this.curves.size()));
					break;
				}
			}
		}
	}

	public void trimTwo(final Path p, final boolean reverse1, final boolean reverse2) {
		final Path untrimmed1 = this.clone();
		final Path untrimmed2 = p.clone();
		this.trim(untrimmed2, reverse1);
		p.trim(untrimmed1, reverse2);
	}

	public Curve[] trimTwoClone(final Curve curve2, final boolean reverse1, final boolean reverse2) {
		final Curve untrimmed1 = this.clone();
		final Curve untrimmed2 = curve2.clone();
		Curve[] output = new Curve[2];
		output[0] = untrimmed1.trimClone(untrimmed2, reverse1);
		output[1] = untrimmed2.trimClone(untrimmed1, reverse2);
		return output;
	}

	public void trimAt(double distance, final boolean reverse) {
		if (reverse)
			this.reverse();

		for (int i = 0; i < this.curves.size(); i++) {
			final Curve iC = this.curves.get(i);
			if (iC.getLength() <= distance) {
				distance -= iC.getLength();
			} else { // Distance kleiner als Curve-Length
				if (iC instanceof LineSeg) {
					final LineSeg iL = (LineSeg) iC;
					this.curves.set(i, new LineSeg(iL.startPoint(), iL.getAngle(), distance));
				} else if (iC instanceof Arc) {
					final Arc iA = (Arc) iC;
					double en = MathUtils.toAngle(distance, iA.getR());
					if (iA.getRotDir() == RotDir.POS) {
						en += iA.getStart();
					} else { // RotDir == NEG
						en = iA.getStart() - en;
					}
					this.curves.set(i, new Arc(iA.getC(), iA.getR(), iA.getStart(), en, iA.getRotDir()));
				}
				this.curves = new ArrayList<Curve>(this.curves.subList(0, i + 1));
				break;
			}
		}
		if (reverse)
			this.reverse();
	}

	public Path trimAtClone(double distance, final boolean reverse) {
		final Path clone = this.clone();
		clone.trimAt(distance, reverse);
		return clone;
	}

	public Curve getFirstCurve() {
		if (this.curves.size() > 0) {
			return this.curves.get(0);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	public Point startPoint() {
		if (this.curves.size() > 0) {
			final Curve first = this.getFirstCurve();
			return first.startPoint();
		} else {
			return null;
		}
	}

	public Curve getLastCurve() {
		if (this.curves.size() > 0) {
			return this.curves.get(this.curves.size() - 1);
		} else {
			return null;
		}
	}

	public Point getEndPoint() {
		if (this.curves.size() > 0) {
			final Curve last = this.getLastCurve();
			return last.getEndPoint();
		} else {
			return null;
		}
	}

	public void setStartPoint(final Point p) {
		this.getFirstCurve().setStartPoint(p);
	}

	public void setEndPoint(final Point p) {
		this.getLastCurve().setEndPoint(p);
	}

	public Line orthoLineEnd() {
		final Curve c = this.getLastCurve();
		if (c instanceof LineSeg) {
			final LineSeg seg = (LineSeg) c;
			return new Line(seg.getEndPoint(), seg.getAngle() + Math.PI / 2.);
		} else if (c instanceof Arc) {
			final Arc arc = (Arc) c;
			return new Line(arc.getEndPoint(), arc.getC());
		} else {
			return null;
		}
	}

	public Line orthoLineStart() {
		final Curve c = this.getFirstCurve();
		if (c instanceof LineSeg) {
			final LineSeg seg = (LineSeg) c;
			return new Line(seg.startPoint(), seg.getAngle() + Math.PI / 2.);
		} else if (c instanceof Arc) {
			final Arc arc = (Arc) c;
			return new Line(arc.startPoint(), arc.getC());
		} else {
			return null;
		}
	}

	public String print() {
		String output = "Path begin\n";
		for (Curve i : curves) {
			output += i.print();
		}
		output += "Path end";
		return output;
	}

	public Point pointAt(double length) {
		final Point p = this.startPoint().clone();
		for (int i = 0; i < this.curves.size() && length > 0; i++) {
			final Curve c = this.curves.get(i);
			final double maxLength = Math.min(length, c.getLength());
			length -= c.getLength();
			if (c instanceof LineSeg) {
				p.move(maxLength, ((LineSeg) c).getAngle());
			} else if (c instanceof Arc) {
				double angle = MathUtils.toAngle(maxLength, ((Arc) c).getR());
				if (((Arc) c).getRotDir() == RotDir.NEG) {
					angle *= -1.;
				}
				p.rotate(((Arc) c).getC(), angle);
			}
		}
		return p;
	}
}
