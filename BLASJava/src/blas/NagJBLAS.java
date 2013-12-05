package blas;

import com.nag.routines.Routine;
import com.nag.routines.F06.DAXPY;
import com.nag.routines.F06.DGEMM;
import com.nag.routines.F06.DGEMV;
import com.nag.routines.G02.G02BX;

public class NagJBLAS extends BLAS {

	public NagJBLAS() {
		super(BlasType.NAGJ);
	}

	@Override
	public long daxpy(double alpha, RealMatrix x, RealMatrix y, int times) {

		int n = x.rows();
		long elapsedTime = 0;

		try {
			Routine.init();
			DAXPY daxpyNagJ = new DAXPY(n, alpha, x.getData().getData(), 1, y
					.getData().getData(), 1);

			long startTime = System.nanoTime();

			daxpyNagJ.eval();

			elapsedTime = System.nanoTime() - startTime;

		} catch (Exception e) {
			System.out.println("Error in NagJ daxpy!");
		}

		return elapsedTime;
	}

	@Override
	public long dgemv(Transpose transA, double alpha, RealMatrix A,
			RealMatrix x, double beta, RealMatrix y, int times) {

		String tA = transA == Transpose.NOTRANSPOSE ? "N" : "T";
		int m = A.rows();
		int n = A.columns();
		int lda = m;
		long elapsedTime = 0;

		try {
			Routine.init();
			DGEMV dgemvNagJ = new DGEMV(tA, m, n, alpha, A.getData().getData(),
					lda, x.getData().getData(), 1, beta, y.getData().getData(),
					1);

			long startTime = System.nanoTime();

			dgemvNagJ.eval();

			elapsedTime = System.nanoTime() - startTime;

		} catch (Exception e) {
			System.out.println("Error in NagJ dgemv!");
		}

		return elapsedTime;
	}

	@Override
	public long dgemm(Transpose transA, Transpose transB, double alpha,
			RealMatrix A, RealMatrix B, double beta, RealMatrix C, int times) {

		String tA = transA == Transpose.NOTRANSPOSE ? "N" : "T";
		String tB = transB == Transpose.NOTRANSPOSE ? "N" : "T";
		int m = transA == Transpose.NOTRANSPOSE ? A.rows() : A.columns();
		int n = transB == Transpose.NOTRANSPOSE ? B.columns() : B.rows();
		int k = transA == Transpose.NOTRANSPOSE ? A.columns() : A.rows();
		int lda = transA == Transpose.NOTRANSPOSE ? m : k;
		int ldb = transB == Transpose.NOTRANSPOSE ? k : n;
		int ldc = m;
		long elapsedTime = 0;

		try {
			Routine.init();
			DGEMM dgemmNagJ = new DGEMM(tA, tB, m, n, k, alpha, A.getData()
					.getData(), lda, B.getData().getData(), ldb, beta, C
					.getData().getData(), ldc);

			long startTime = System.nanoTime();

			dgemmNagJ.eval();

			elapsedTime = System.nanoTime() - startTime;

		} catch (Exception e) {
			System.out.println("Error in NagJ dgemm!");
		}

		return elapsedTime;
	}

	@Override
	public long covMtx(RealMatrix A, RealMatrix weightVector,
			RealMatrix varCovarMtx, int times) {

		int n = A.rows();
		int m = A.columns();
		int ldx = n;
		int ldv = m;

		long elapsedTime = 0;

		try {
			Routine.init();
			G02BX covNagJ = new G02BX("W", n, m, A.getData().getData(), ldx, weightVector.getData().getData(), new double[m],
					new double[m], varCovarMtx.getData().getData(), ldv, new double[m*m], -1);

			long startTime = System.nanoTime();

			covNagJ.eval();

			elapsedTime = System.nanoTime() - startTime;

		} catch (Exception e) {
			System.out.println("Error in NagJ covMtx!");
		}

		return elapsedTime;
	}

	@Override
	public String toString() {
		return "NagJ_BLAS";
	}
}
