package blas;

import nativeInterface.BLASIntelMKL;
import nativeInterface.swigDoubleArray;

public class IntelMKLBLAS extends BLAS {

	private BLASIntelMKL bmkl;

	public IntelMKLBLAS() {
		super(BlasType.INTELMKL);
		this.bmkl = new BLASIntelMKL();
	}

	@Override
	public long daxpy(double alpha, RealMatrix x, RealMatrix y, int times) {

		int n = x.rows();

		swigDoubleArray xSWIG = x.getData().toSwigDoubleArray();
		swigDoubleArray ySWIG = y.getData().toSwigDoubleArray();
		
		long startTime = System.nanoTime();
		
		bmkl.daxpy(n, alpha, xSWIG.cast(), 1, ySWIG.cast(), 1);
		
		long elapsedTime = System.nanoTime() - startTime;
		
		y.setData((new RealArray(ySWIG, y.getData().getLength())));
		return elapsedTime;
	}

	@Override
	public long dgemv(Transpose transA, double alpha, RealMatrix A,
			RealMatrix x, double beta, RealMatrix y, int times) {

		int CBLAS_ORDER = 102;
		int CBLAS_TRANSPOSE_A = transA == Transpose.NOTRANSPOSE ? 111 : 112;
		int m = A.rows();
		int n = A.columns();
		int lda = m;

		swigDoubleArray ASWIG = A.getData().toSwigDoubleArray();
		swigDoubleArray xSWIG = x.getData().toSwigDoubleArray();
		swigDoubleArray ySWIG = y.getData().toSwigDoubleArray();
		
		long startTime = System.nanoTime();

		bmkl.dgemv(CBLAS_ORDER, CBLAS_TRANSPOSE_A, m, n, alpha, ASWIG.cast(),
				lda, xSWIG.cast(), 1, beta, ySWIG.cast(), 1);
		
		long elapsedTime = System.nanoTime() - startTime;

		y.setData((new RealArray(ySWIG, y.getData().getLength())));
		return elapsedTime;
	}

	@Override
	public long dgemm(Transpose transA, Transpose transB, double alpha,
			RealMatrix A, RealMatrix B, double beta, RealMatrix C, int times) {

		int CBLAS_ORDER = 102;
		int CBLAS_TRANSPOSE_A = transA == Transpose.NOTRANSPOSE ? 111 : 112;
		int CBLAS_TRANSPOSE_B = transB == Transpose.NOTRANSPOSE ? 111 : 112;
		int m = transA == Transpose.NOTRANSPOSE ? A.rows() : A.columns();
		int n = transB == Transpose.NOTRANSPOSE ? B.columns() : B.rows();
		int k = transA == Transpose.NOTRANSPOSE ? A.columns() : A.rows();
		int lda = transA == Transpose.NOTRANSPOSE ? m : k;
		int ldb = transB == Transpose.NOTRANSPOSE ? k : n;
		int ldc = m;

		swigDoubleArray ASWIG = A.getData().toSwigDoubleArray();
		swigDoubleArray BSWIG = B.getData().toSwigDoubleArray();
		swigDoubleArray CSWIG = C.getData().toSwigDoubleArray();
		
		long startTime = System.nanoTime();

		bmkl.dgemm(CBLAS_ORDER, CBLAS_TRANSPOSE_A, CBLAS_TRANSPOSE_B, m, n, k,
				alpha, ASWIG.cast(), lda, BSWIG.cast(), ldb, beta,
				CSWIG.cast(), ldc);
		
		long elapsedTime = System.nanoTime() - startTime;

		C.setData((new RealArray(CSWIG, m * n)));
		return elapsedTime;
	}

	@Override
	public String toString() {
		return "IntelMKL_BLAS";
	}
}
