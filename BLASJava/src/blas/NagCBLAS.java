package blas;

import nativeInterface.BLASNagC;
import nativeInterface.SWIGTYPE_p_double;

public class NagCBLAS extends BLAS {

	private BLASNagC bnc;

	public NagCBLAS() {
		this.bnc = new BLASNagC();
	}

	@Override
	public void daxpy(int n, double alpha, RealArray x, int incx, RealArray y,
			int incy, int times) {
		SWIGTYPE_p_double yAsSwigtype = y.toSWIGTYPE_p_double();
		callDaxpyMultiple(n, alpha, x.toSWIGTYPE_p_double(), incx, yAsSwigtype,
				incy, times);
		y.setData((new RealArray(yAsSwigtype, n)).getData());
	}

	// Method to be measured
	private void callDaxpyMultiple(int n, double alpha, SWIGTYPE_p_double x,
			int incx, SWIGTYPE_p_double y, int incy, int times) {
		for (int i = 0; i < times; i++) {
			bnc.daxpby(n, alpha, x, incx, 1, y, incy);
		}
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
		return "NagC_BLAS";
	}

	/*
	 * public void daxpy(int n, double alpha, SWIGTYPE_p_double x, int incx,
	 * SWIGTYPE_p_double y, int incy) { bnc.daxpby(n, alpha, x, incx, 1, y,
	 * incy); }
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
