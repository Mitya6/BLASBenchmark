package blas;

public abstract class BLAS {

	// BLAS functions

	// daxpy: y <- alpha*x + y (intelMKL only)
	// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
	public abstract void daxpy(int n, double alpha, RealArray x, int incx,
			RealArray y, int incy, int times);

	// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
	public abstract void dgemv(MatrixOrder order, char transA, int m, int n,
			double alpha, RealArray A, int lda, RealArray x, int incx,
			double beta, RealArray y, int incy, int times);

	// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
	public abstract void dgemm(MatrixOrder order, char transA, char transB,
			int m, int n, int k, double alpha, RealArray A, int lda,
			RealArray B, int ldb, double beta, RealArray C, int ldc, int times);

	// Covariance functions

	// Covariance Matrix function call if supported otherwise calls primitive
	public abstract void covMtx(MatrixOrder order, int m, int n, RealArray A,
			int lda, int ldb, RealArray result, int ldc, int times);

	// Covariance Matrix based on BLAS primitive functions
	public abstract void covMtxPrimitive(MatrixOrder order, int m, int n,
			RealArray A, int lda, int ldb, RealArray result, int ldc, int times);

	@Override
	public abstract String toString();
}
