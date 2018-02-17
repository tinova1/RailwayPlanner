package guiTransfer;

public class ParseInput {
	public static double parseLength(String input) throws NumberFormatException {
		cleanString(input);
		double inputVal = Double.parseDouble(onlyDigits(input));

		if (input.endsWith("um")) {
			inputVal /= 1000.;
		} else if (input.endsWith("mm")) {

		} else if (input.endsWith("cm")) {
			inputVal /= .1;
		} else if (input.endsWith("dm")) {
			inputVal /= .01;
		} else if (input.endsWith("m")) {
			inputVal /= .001;
		} else if (input.endsWith("ft")) {
			inputVal *= 304.8;
		} else if (input.endsWith("in") || input.endsWith("inch") || input.endsWith("\"")) {
			inputVal *= 25.4;
		}
		return inputVal;
	}

	public static double parseAngle(String input) throws NumberFormatException {
		// angle in degrees
		cleanString(input);
		double inputVal = Double.parseDouble(onlyDigits(input));
		if (input.contains(":")) {
			String[] numDenum = input.split(":");
			for (int i = 0; i < numDenum.length; i++) {
				numDenum[i] = onlyDigits(numDenum[i]);
			}
			inputVal = Math.atan2(Double.parseDouble(numDenum[0]), Double.parseDouble(numDenum[1]));
		} else if (input.endsWith("rad") || input.endsWith("r")) {
			inputVal = Math.toDegrees(inputVal);
		}
		return inputVal;
	}

	private static void cleanString(String input) {
		input.trim();
		input.replace(',', '.');
		input.replace('/', ':');
	}

	private static String onlyDigits(String input) {
		String cleanInput = "";
		for (int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
				cleanInput += input.charAt(i);
			}
		}
		return cleanInput;
	}
}
