package benchmark;

import java.util.ArrayList;

import blas.BLAS;
import blas.JBlasBLAS;
import blas.MatrixOrder;
import blas.NagCBLAS;
import blas.RealMatrix;
import blas.Result;

public class Benchmark {

	private static ArrayList<BLAS> blasList;

	public static void main(String[] args) {

		blasList = new ArrayList<BLAS>();
		blasList.add(new JBlasBLAS());
		//blasList.add(new NagCBLAS());

		// daxpyRandomTest(5, 1);
		// daxpyRandomTest(5000, 100000);

		//covarianceRandomTest(5, 3, 1);
		
		ArrayList<double[]> dataSet = CsvReader.readData();
		
		RealMatrix priceMatrix = new RealMatrix(220, 33, dataSet.get(0), MatrixOrder.RowMajor);
		priceMatrix.setOrder(MatrixOrder.ColMajor);		
		
		RealMatrix covMatrix = new RealMatrix(33, 33, dataSet.get(1), MatrixOrder.RowMajor);
		covMatrix.setOrder(MatrixOrder.ColMajor);
		
		covarianceTest(priceMatrix.rows(), priceMatrix.columns(), priceMatrix, 1);
	}

	

	private static void covarianceRandomTest(int m, int k, int times) {

		// init random matrix
		RealMatrix A = RealMatrix.createRandomDouble01(m, k,
				MatrixOrder.ColMajor);

		covarianceTest(m, k, A, times);
	}

	private static RealMatrix covarianceTest(int k, int n, RealMatrix A, int times) {
		RealMatrix retval = null;
		
		for (BLAS b : blasList) {

			RealMatrix result = new RealMatrix(n, n, MatrixOrder.ColMajor);

			try {
				b.covMtxPrimitive(MatrixOrder.ColMajor, k, n, A.data, k, k,
						result.data, n, times);
			} finally {
				System.out.println("\n\n========== cov Random (x" + times
						+ ") ==========\n");
				//System.out.println("A:\n" + A + "\n");
				System.out.println("result:\n" + result + "\n");
			}
			retval = result;
		}
		
		return retval;
	}

	private static ArrayList<Result> daxpyRandomTest(int m, int times) {
		ArrayList<Result> results = new ArrayList<Result>();

		// init daxpy
		RealMatrix x1 = RealMatrix.createRandomDouble01(m, 1,
				MatrixOrder.ColMajor);
		RealMatrix y1 = RealMatrix.createRandomDouble01(m, 1,
				MatrixOrder.ColMajor);

		for (BLAS b : blasList) {

			RealMatrix y1LocalCopy = y1.copy();

			try {
				b.daxpy(x1.rows(), 2, x1.data, 1, y1LocalCopy.data, 1, times);
			} finally {
				System.out.println("\n\n========== DAXPY (x" + times
						+ ") ==========\n");
				System.out.println("x1:\n" + x1 + "\n");
				System.out.println("y1 (original):\n" + y1 + "\n");
				System.out.println("y1:\n" + y1LocalCopy + "\n");
			}
		}

		return results;
	}

	static {
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\OnlabII\\BLASBenchmark\\BlasCpp\\Debug\\BlasCpp.dll");
	}

}
