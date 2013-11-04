package blas;

import nativeInterface.BLASNagC;
import nativeInterface.SWIGTYPE_p_double;

public class NagCBLAS extends BLAS {

	private BLASNagC bnc;

	public NagCBLAS() {
		this.bnc = new BLASNagC();
	}

	public void daxpy(int n, double alpha, SWIGTYPE_p_double x, int incx,
			SWIGTYPE_p_double y, int incy) {
		bnc.daxpby(n, alpha, x, incx, 1, y, incy);
	}

	public void dgemv(int order, char transA, int m, int n, double alpha,
			double A[], int lda, double x[], int incx, double beta, double y[],
			int incy) {
	}

	public void dgemm(int order, char transA, char transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc) {
	}
}
