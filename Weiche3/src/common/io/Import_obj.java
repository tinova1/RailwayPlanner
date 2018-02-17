package common.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import common.vectorMath.Polyhedron;
import common.vectorMath.objects3D.Point;

public abstract class Import_obj {

	public static Polyhedron import_obj(String fileName) {

		ArrayList<Point> verts = new ArrayList<>();
		ArrayList<int[]> faces = new ArrayList<>();

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

		return new Polyhedron(verts, faces);
	}
}
