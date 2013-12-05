package benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CsvReader {
	
	public static double[] loadPriceData() {
		return readData("..\\..\\devizaarfolyamok_2013.csv", 220, 33);
	}
	
	public static double[] loadCovarianceData() {
		return readData("..\\..\\devizaarfolyamok_cov.csv", 33, 33);
	}

	private static double[] readData(String filename, int rows, int columns) {

		double[] data = new double[rows * columns];
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
			scanner.useDelimiter(";");

			int i = 0;
			while (scanner.hasNext() && i < rows * columns) {
				data[i] = Double.parseDouble(scanner.next());
				i++;
			}

		} catch (FileNotFoundException e) {
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return data;
	}
}
