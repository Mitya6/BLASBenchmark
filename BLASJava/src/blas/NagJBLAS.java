package blas;

public class NagJBLAS extends BLAS {

	@Override
	public void daxpy(int n, double alpha, RealArray x, int incx, RealArray y,
			int incy, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dgemv(MatrixOrder order, char transA, int m, int n,
			double alpha, RealArray A, int lda, RealArray x, int incx,
			double beta, RealArray y, int incy, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dgemm(MatrixOrder order, char transA, char transB, int m,
			int n, int k, double alpha, RealArray A, int lda, RealArray B,
			int ldb, double beta, RealArray C, int ldc, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	public void covMtx(MatrixOrder order, int m, int n, RealArray A, int lda,
			int ldb, RealArray result, int ldc, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	public void covMtxPrimitive(MatrixOrder order, int m, int n, RealArray A,
			int lda, int ldb, RealArray result, int ldc, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "NagJ_BLAS";
	}

	/*
	 * public void daxpy(int n, double alpha, double x[], int incx, double y[],
	 * int incy) { }
	 * 
	 * public void dgemv(int order, char transA, int m, int n, double alpha,
	 * double A[], int lda, double x[], int incx, double beta, double y[], int
	 * incy) { }
	 * 
	 * public void dgemm(int order, char transA, char transB, int m, int n, int
	 * k, double alpha, double A[], int lda, double B[], int ldb, double beta,
	 * double C[], int ldc) { }
	 */
}
