package common.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Import_csv {
	public static List<String[]> import_csv(final String fileName) {
		final List<String[]> rows = new ArrayList<>();

		try {
			File obj = new File(fileName);
			Scanner sc = new Scanner(obj);

			while (sc.hasNextLine()) {
				final String line = sc.nextLine();
				final String[] cells = line.split(";");
				rows.add(cells);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden!");
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(1);
		}
		return rows;
	}
}
