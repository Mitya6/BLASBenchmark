package blas;

import org.jblas.NativeBlas;

public class JBlasBLAS extends BLAS {

	public void daxpy(int n, double alpha, double x[], int incx, double y[], int incy) {
		NativeBlas.daxpy(n, alpha, x, 0, incx, y, 0, incy);
	}

	public void dgemv(int order, char transA, int m, int n, double alpha, double A[],
			int lda, double x[], int incx, double beta, double y[], int incy) {
		NativeBlas.dgemv(transA, m, n, alpha, A, 0, lda, x, 0, 1, beta, y, 0, 1);
	}

	public void dgemm(int order, char transA, char transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc) {
		NativeBlas.dgemm(transA, transB, m, n, k, alpha, A, 0, lda, B, 0, 
				ldb, beta, C, 0, ldc);
	}
}
