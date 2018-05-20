package views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.io.Export_csv;
import common.io.Import_csv;

public class CSVExport {
	/*
	 * Here, all values in the GUI are stored
	 */
	String fileName = "export";
	final String defaultFileName = "default.csv"; // only needed to fill boxes at initialization
	/*
	 * These are Indices that mark in which array index the specified value can be
	 * found
	 */
	final int gaugeI = 0;
	final int radiusLeftI = 1;
	final int radiusRightI = 2;
	final int radiusApproI = 3;
	final int angleLeftI = 4;
	final int angleRightI = 5;
	final int angleApproI = 6;
	final int strLeftI = 7;
	final int strRightI = 8;
	final int strApproI = 9;
	final int offsLeftI = 10;
	final int offsRightI = 11;
	final int footI = 12;
	final int headI = 13;
	final int vFrogI = 14;
	final int kFrogI = 15;
	final int guideI = 16;
	final int flangeI = 17;
	final int typeI = 18; //0,1,2 depending on type

	final List<String[]> paramTable;

	CSVExport() {
		paramTable = Import_csv.import_csv(defaultFileName);
	}

	void exportCSV() {
		final long milliseconds = System.currentTimeMillis();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd_hh-mm");
		Date date = new Date(milliseconds);
		fileName = sdf.format(date);
		Export_csv.export_csv(fileName, paramTable);
	}
}
