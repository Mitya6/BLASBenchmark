package benchmark;

import java.util.ArrayList;

import blas.BLAS;
import blas.BlasMethod;
import blas.MatrixOrder;
import blas.RealMatrix;
import blas.Transpose;

public class TimeTest {

	public static ArrayList<TestResult> dgemmTest(ArrayList<BLAS> blasList,
			int m, int n, int k, int repeatCount) {

		// Matrix A
		RealMatrix A = RealMatrix.createRandomDouble01(m, k,
				MatrixOrder.COLMAJOR);

		// Matrix B
		RealMatrix B = RealMatrix.createRandomDouble01(k, n,
				MatrixOrder.COLMAJOR);

		ArrayList<TestResult> testResults = new ArrayList<TestResult>();

		for (BLAS b : blasList) {
			long nanoTime = 0;
			try {
				for (int i = -1; i < repeatCount; i++) {

					// first run is a warmup run
					if (i == -1) {
						b.dgemm(Transpose.NOTRANSPOSE, Transpose.NOTRANSPOSE,
								1.0, A, B, 0.0, new RealMatrix(m, n,
										MatrixOrder.COLMAJOR), 1);
					} else {
						A.swapColumns();
						nanoTime += b.dgemm(Transpose.NOTRANSPOSE,
								Transpose.NOTRANSPOSE, 1.0, A, B, 0.0,
								new RealMatrix(m, n, MatrixOrder.COLMAJOR), 1);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				testResults.add(new TestResult(b.blasType, BlasMethod.DGEMM,
						repeatCount, m, n, k, nanoTime));
			}
		}

		return testResults;
	}

	public static ArrayList<TestResult> covTest(ArrayList<BLAS> blasList,
			RealMatrix A, int obs, int vars, int repeatCount) {
		return covTest(blasList, A, obs, vars, repeatCount, false);
	}

	public static ArrayList<TestResult> covPrimitiveTest(
			ArrayList<BLAS> blasList, RealMatrix A, int obs, int vars,
			int repeatCount) {
		return covTest(blasList, A, obs, vars, repeatCount, true);
	}

	private static ArrayList<TestResult> covTest(ArrayList<BLAS> blasList,
			RealMatrix A, int obs, int vars, int repeatCount, boolean primitive) {

		ArrayList<TestResult> testResults = new ArrayList<TestResult>();

		// Use random data if no prepared data were provided
		if (A == null) {
			A = RealMatrix
					.createRandomDouble01(obs, vars, MatrixOrder.COLMAJOR);
		}

		RealMatrix weightVector = CovarianceHelper.getWeigthVector(A.rows());

		for (BLAS b : blasList) {
			long nanoTime = 0;
			try {
				for (int i = -1; i < repeatCount; i++) {

					A.swapColumns();
					RealMatrix Acopy = A.copy();
					RealMatrix varCovarMtx = new RealMatrix(A.columns(),
							A.columns(), MatrixOrder.COLMAJOR);

					// first run is a warmup run
					if (i == -1) {
						if (primitive) {
							b.covMtxPrimitive(Acopy, weightVector, varCovarMtx,
									1);
						} else {
							b.covMtx(Acopy, weightVector, varCovarMtx, 1);
						}
					} else {
						if (primitive) {
							nanoTime += b.covMtxPrimitive(Acopy, weightVector,
									varCovarMtx, 1);
						} else {
							nanoTime += b.covMtx(Acopy, weightVector,
									varCovarMtx, 1);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				testResults.add(new TestResult(b.blasType,
						primitive ? BlasMethod.COVPRIMITIVE : BlasMethod.COV,
						repeatCount, A.rows(), A.columns(), 0, nanoTime));
			}

		}

		return testResults;
	}
}
