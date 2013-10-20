package blas;

public class IntelMKLBLAS extends BLAS {

	public void daxpy(int n, double alpha, double x[], int incx, double y[], int incy) {
	}

	public void dgemv(int order, char transA, int m, int n, double alpha, double A[],
			int lda, double x[], int incx, double beta, double y[], int incy) {
	}

	public void dgemm(int order, char transA, char transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc) {
	}
}
