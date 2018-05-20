package common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Export_csv {
	
	private static char separationChar = ',';
	
	public static void export_csv(String fileName, List<String[]> table) {
		try {
			FileWriter writer = new FileWriter(new File(fileName + ".csv"));
			
			for(String[] row : table) {
				for(String cell : row) {
					writer.write(cell+separationChar);
				}
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben der Datei");
			e.getLocalizedMessage();
			System.exit(1);
		}
	}
}
