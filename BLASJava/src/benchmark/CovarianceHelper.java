package benchmark;

import blas.MatrixOrder;
import blas.RealMatrix;

public class CovarianceHelper {

	public static RealMatrix getWeigthVector(int size) {

		// 1) weights for each row
		double[] weights = new double[size];
		double weightSum = 0.0;
		for (int i = 0; i < size; i++) {
			double weight = Math.pow(2, (i - size) / 30.0);
			weights[i] = weight;
			weightSum += weight;
		}

		// 2) rescaled weights
		for (int i = 0; i < size; i++) {
			weights[i] *= size / weightSum;
		}

		return new RealMatrix(size, 1, weights, MatrixOrder.COLMAJOR);
	}

	public static RealMatrix getReferenceMatrix() {

		double[] data = CsvReader.loadCovarianceData();

		RealMatrix res = new RealMatrix(33, 33, data, MatrixOrder.ROWMAJOR);
		res.setOrder(MatrixOrder.COLMAJOR);
		return res;
	}

	public static RealMatrix getAMatrix() {

		double[] data = CsvReader.loadPriceData();

		RealMatrix res = new RealMatrix(220, 33, data, MatrixOrder.ROWMAJOR);
		res.setOrder(MatrixOrder.COLMAJOR);
		return res;
	}

}
