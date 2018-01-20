package common.svgCreator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SVGExporter {
	public static void writeFile(final SVGFile svgFile, final String fileName) {
		try {
			FileWriter writer = new FileWriter(new File(fileName));
			writer.write(svgFile.getFile());
			writer.close();
		} catch (IOException e) {
			e.getLocalizedMessage();
		}
	}
}
