package blas;

import java.util.Arrays;

import org.jblas.NativeBlas;

public class JBlasBLAS extends BLAS {

	@Override
	public void daxpy(int n, double alpha, RealArray x, int incx, RealArray y,
			int incy, int times) {

		double[] yAsDouble = y.toDouble();
		callDaxpyMultiple(n, alpha, x.toDouble(), incx, yAsDouble, incy, times);
		y = new RealArray(yAsDouble);
	}

	// Method to be measured
	private void callDaxpyMultiple(int n, double alpha, double[] x, int incx,
			double[] y, int incy, int times) {

		for (int i = 0; i < times; i++) {
			NativeBlas.daxpy(n, alpha, x, 0, incx, y, 0, incy);
		}
	}

	@Override
	public void dgemv(MatrixOrder order, char transA, int m, int n,
			double alpha, RealArray A, int lda, RealArray x, int incx,
			double beta, RealArray y, int incy, int times) {

		double[] yAsDouble = y.toDouble();
		callDgemvMultiple(transA, m, n, alpha, A.toDouble(), lda, x.toDouble(),
				incx, beta, yAsDouble, incy, times);
		y = new RealArray(yAsDouble);
	}

	// Method to be measured
	private void callDgemvMultiple(char transA, int m, int n, double alpha,
			double[] A, int lda, double[] x, int incx, double beta, double[] y,
			int incy, int times) {

		for (int i = 0; i < times; i++) {
			NativeBlas.dgemv(transA, m, n, alpha, A, 0, lda, x, 0, 1, beta, y,
					0, 1);
		}
	}

	@Override
	public void dgemm(MatrixOrder order, char transA, char transB, int m,
			int n, int k, double alpha, RealArray A, int lda, RealArray B,
			int ldb, double beta, RealArray C, int ldc, int times) {

		double[] CAsDouble = C.toDouble();
		callDgemmMultiple(transA, transB, m, n, k, alpha, A.toDouble(), lda,
				B.toDouble(), ldb, beta, CAsDouble, ldc, times);
		C = new RealArray(CAsDouble);
	}

	// Method to be measured
	private void callDgemmMultiple(char transA, char transB, int m, int n,
			int k, double alpha, double[] A, int lda, double[] B, int ldb,
			double beta, double[] C, int ldc, int times) {

		for (int i = 0; i < times; i++) {
			NativeBlas.dgemm(transA, transB, m, n, k, alpha, A, 0, lda, B, 0,
					ldb, beta, C, 0, ldc);
		}
	}

	@Override
	public void covMtx(MatrixOrder order, int m, int n, RealArray A, int lda,
			int ldb, RealArray result, int ldc, int times) {
		covMtxPrimitive(order, m, n, A, lda, ldb, result, ldc, times);
	}

	@Override
	public void covMtxPrimitive(MatrixOrder order, int m, int n, RealArray A,
			int lda, int ldb, RealArray result, int ldc, int times) {

		double[] resultAsDouble = result.toDouble();
		callCovMtxPrimitiveMultiple(m, n, A.toDouble(), lda, ldb,
				resultAsDouble, ldc, times);
		result = new RealArray(resultAsDouble);

		// create and pass nxn identityMatrix
	}

	// Method to be measured
	// In JBlas use colmajor order
	private void callCovMtxPrimitiveMultiple(int m, int n, double[] A, int lda,
			int ldb, /* double[] identityMtx, */double[] result, int ldc,
			int times) {

		// 1) weights for each row
		double[] weights = new double[m];
		double weightSum = 0.0;
		for (int i = 0; i < m; i++) {
			double weight = Math.pow(2, (i - m) / 30.0);
			weights[i] = weight;
			weightSum += weight;
		}

		// 2) rescaled weights
		for (int i = 0; i < m; i++) {
			weights[i] *= m / weightSum;
		}

		// 3) weighted average for each column
		double[] weightedAvgs = new double[n];
		for (int j = 0; j < n; j++) {
			double weightedAvg = 0.0;
			for (int i = 0; i < m; i++) {
				weightedAvg += weights[i] * A[j * m + i];
			}
			weightedAvgs[j] = weightedAvg / m;
		}

		// 4) centered data => m(i,j) - weightedAvg(j)
		// dgemm: C <- alpha*op(A)*op(B) + beta*C
		// 1*A*I + 1*weightMtx

		double[] centeredData = new double[m * n];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				centeredData[j * m + i] = A[j * m + i] - weightedAvgs[j];
			}
		}

		/*
		 * double[] weightMtx = new double[m * n]; for (int j = 0; j < n; j++) {
		 * for (int i = 0; i < m; i++) { weightMtx[j * m + i] =
		 * -weightedAvgs[j]; } } callDgemmMultiple('N', 'N', m, n, n, 1, A, m,
		 * identityMtx, n, 1, weightMtx, m, 1);
		 * 
		 * double[] centeredData = weightMtx;
		 */

		// 5) weighted centered data => centered data(i,j)*weights(i)
		double[] weightedCenteredData = new double[m * n];
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				weightedCenteredData[j * m + i] = centeredData[j * m + i]
						* weights[i];
			}
		}

		// 6) calculate cov mtx
		// double[] result = new double[n * n];

		callDgemmMultiple('T', 'N', n, n, m, 1.0 / (m - 1), centeredData, lda,
				weightedCenteredData, ldb, 0.0, result, ldc, 1);
	}

	@Override
	public String toString() {
		return "JBlas_BLAS";
	}
}
