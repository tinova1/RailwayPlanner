package common.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.vectorMath.objects3D.Point;
import common.vectorMath.objects3D.Polyhedron;

public abstract class Import_obj {

	public static Polyhedron import_obj(final String fileName) {

		final List<Point> verts = new ArrayList<>();
		final List<int[]> faces = new ArrayList<>();

		try {
			File obj = new File(fileName);
			Scanner sc = new Scanner(obj);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.startsWith("v ")) {
					verts.add(IO_obj_Utils.lineToPoint(line));
				} else if (line.startsWith("f ")) {
					faces.add(IO_obj_Utils.lineToFace(line));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei Kleineisen nicht gefunden!");
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
		Polyhedron poly = new Polyhedron(verts, faces);
		poly.updateTagLocal();
		return poly;
	}
}
