package blas;

import nativeInterface.BLASCppModule;
import nativeInterface.BLASNagC;
import nativeInterface.SWIGTYPE_p_double;

public class NagCBLAS extends BLAS {

	private BLASNagC bnc;

	public NagCBLAS() {
		this.bnc = new BLASNagC();
	}

	public void daxpy(int n, double alpha, double x[], int incx, double y[], int incy) {

		SWIGTYPE_p_double xSwigArray = BLASCppModule.new_doubleArray(n);
		SWIGTYPE_p_double ySwigArray = BLASCppModule.new_doubleArray(n);
		
		for (int i = 0; i < n; i++) {
			BLASCppModule.doubleArray_setitem(xSwigArray, i, x[i]);
			BLASCppModule.doubleArray_setitem(ySwigArray, i, y[i]);
		}

		bnc.daxpby(n, alpha, xSwigArray, incx, 1, ySwigArray, incy);
		
		for (int i = 0; i < n; i++) {
			x[i] = BLASCppModule.doubleArray_getitem(xSwigArray, i);
			y[i] = BLASCppModule.doubleArray_getitem(ySwigArray, i);
		}
		
		BLASCppModule.delete_doubleArray(xSwigArray);
		BLASCppModule.delete_doubleArray(ySwigArray);
	}

	public void dgemv(int order, char transA, int m, int n, double alpha, double A[],
			int lda, double x[], int incx, double beta, double y[], int incy) {
	}

	public void dgemm(int order, char transA, char transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc) {
	}
}
