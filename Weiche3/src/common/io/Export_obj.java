package common.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.components.Kleineisen;
import common.components.Tie;
import common.geometry.Canvas;
import common.railway.Railway;

public abstract class Export_obj {

	public static void ausgabe(String fileName, Canvas c) {
		try {
			FileWriter ausgabe = new FileWriter(new File(fileName + ".obj"));
			for (Railway w : c.getRailwayList()) {
				ArrayList<Tie> tie_list = w.getTieBand().getTieList();
				for (int i = 0; i < tie_list.size(); i++) {
					final Tie tie = tie_list.get(i);
					ausgabe.write("g Tie" + tie.hashCode() + "\n\n");
					ausgabe.write(tie.getCube().export_obj());

					List<Kleineisen> kleinList = tie.getKleinList();
					for (int j = 0; j < kleinList.size(); j++) {
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
