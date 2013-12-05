package blas;

import org.jblas.NativeBlas;

public class JBlasBLAS extends BLAS {
	
	public JBlasBLAS() {
		super(BlasType.JBLAS);
	}

	@Override
	public long daxpy(double alpha, RealMatrix x, RealMatrix y, int times) {

		int n = x.rows();
		
		long startTime = System.nanoTime();
		
		NativeBlas.daxpy(n, alpha, x.getData().getData(), 0, 1, y.getData()
				.getData(), 0, 1);
		
		long elapsedTime = System.nanoTime() - startTime;
		return elapsedTime;
	}

	@Override
	public long dgemv(Transpose transA, double alpha, RealMatrix A,
			RealMatrix x, double beta, RealMatrix y, int times) {

		char tA = transA == Transpose.NOTRANSPOSE ? 'N' : 'T';
		int m = A.rows();
		int n = A.columns();
		int lda = m;

		long startTime = System.nanoTime();
		
		NativeBlas.dgemv(tA, m, n, alpha, A.getData().getData(), 0, lda, x
				.getData().getData(), 0, 1, beta, y.getData().getData(), 0, 1);
		
		long elapsedTime = System.nanoTime() - startTime;
		return elapsedTime;
	}

	@Override
	public long dgemm(Transpose transA, Transpose transB, double alpha,
			RealMatrix A, RealMatrix B, double beta, RealMatrix C, int times) {

		char tA = transA == Transpose.NOTRANSPOSE ? 'N' : 'T';
		char tB = transB == Transpose.NOTRANSPOSE ? 'N' : 'T';
		int m = transA == Transpose.NOTRANSPOSE ? A.rows() : A.columns();
		int n = transB == Transpose.NOTRANSPOSE ? B.columns() : B.rows();
		int k = transA == Transpose.NOTRANSPOSE ? A.columns() : A.rows();
		int lda = transA == Transpose.NOTRANSPOSE ? m : k;
		int ldb = transB == Transpose.NOTRANSPOSE ? k : n;
		int ldc = m;
		
		long startTime = System.nanoTime();

		NativeBlas.dgemm(tA, tB, m, n, k, alpha, A.getData().getData(), 0, lda,
				B.getData().getData(), 0, ldb, beta, C.getData().getData(), 0,
				ldc);
		
		long elapsedTime = System.nanoTime() - startTime;
		return elapsedTime;
	}	

	@Override
	public String toString() {
		return "JBlas_BLAS";
	}
}
