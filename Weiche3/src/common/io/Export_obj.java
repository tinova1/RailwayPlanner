package common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import common.components.Kleineisen;
import common.components.Tie;
import common.model.railway.Railway;

public abstract class Export_obj {

	public static void ausgabe(String fileName, Railway w) {
		ArrayList<Tie> tie_list = w.getTieBand().getTieList();

		try {
			FileWriter ausgabe = new FileWriter(new File(fileName + ".obj"));

			for (int i = 0; i < tie_list.size(); i++) {
				ausgabe.write("g Tie" + tie_list.get(i).hashCode() + "\n\n");
				ausgabe.write(tie_list.get(i).getCube().export_obj());
				ArrayList<Kleineisen> kleinList = tie_list.get(i).getKleinList();
				for (int j = 0; j < kleinList.size(); j++) {
					if (kleinList.get(j).getActive()) {
						ausgabe.write("g Kleineisen" + kleinList.get(j).hashCode() + "\n\n");
						ausgabe.write(kleinList.get(j).getPoly().export_obj());
					}
				}
			}

			ausgabe.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben der Datei");
			e.getLocalizedMessage();
			System.exit(1);
		}
	}

}
