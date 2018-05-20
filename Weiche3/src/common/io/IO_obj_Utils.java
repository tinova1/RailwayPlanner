package common.io;

import common.vectorMath.MathUtils;
import common.vectorMath.objects3D.Point;

public abstract class IO_obj_Utils {

	static Point lineToPoint(String line) {
		if (line.startsWith("v ")) {
			String[] values = line.split(" ");
			double x = Double.parseDouble(values[1]);
			double y = Double.parseDouble(values[2]);
			double z = Double.parseDouble(values[3]);
			return new Point(x, y, z);
		} else {
			return new Point(Double.NaN, Double.NaN, Double.NaN);
		}
	}

	public static String pointToLine(Point vert) {
		int frac = 5;
		double x = MathUtils.round(vert.getX(), frac);
		double y = MathUtils.round(vert.getY(), frac);
		double z = MathUtils.round(vert.getZ(), frac);
		return "v " + x + " " + y + " " + z + "\n";
	}

	static int[] lineToFace(String line) {
		if (line.startsWith("f ")) {
			String[] raw = line.split(" ");
			int[] ausgabe = new int[raw.length - 1];
			for (int i = 1; i < raw.length; i++) {
				ausgabe[i - 1] = Integer.parseInt(stopAt(raw[i], '/'));
			}
			return ausgabe;
		} else {
			return new int[0];
		}
	}

	public static String faceToLine(int[] face) {
		String output = "f ";
		for (int i = 0; i < face.length; i++) {
			output += face[i] + " ";
		}
		return output + "\n";
	}

	private static String stopAt(final String string, final char endChar) {
		int i;
		for (i = 0; i < string.length(); i++) 
			if (string.charAt(i) == endChar) 
				break;
		return string.substring(0, i);
	}

}
