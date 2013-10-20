package blas;

public abstract class BLAS {

	// BLAS functions

	// daxpy: y <- alpha*x + y (intelMKL only)
	public void daxpy(int n, double alpha, double x[], int incx, double y[], int incy) {
	}

	// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
	/*void daxpby(int n, double alpha, double x[], int incx, double beta,
			double y[], int incy) {
	}*/

	// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
	public void dgemv(int order, char transA, int m, int n, double alpha, double A[],
			int lda, double x[], int incx, double beta, double y[], int incy) {
	}

	// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
	public void dgemm(int order, char transA, char transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc) {
	}
}
