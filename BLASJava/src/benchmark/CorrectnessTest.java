package benchmark;

import java.util.ArrayList;

import blas.BLAS;
import blas.MatrixOrder;
import blas.RealMatrix;
import blas.Transpose;

public class CorrectnessTest {

	// Test if daxpy functions give correct results
	// 5   1   6
	// 4 + 2 = 6
	// 3   3   6
	public static boolean daxpyTest(ArrayList<BLAS> blasList,
			boolean writeOutput) {
		
		String newline = System.getProperty("line.separator");

		int n = 3;
		RealMatrix x = new RealMatrix(n, 1, new double[] { 1, 2, 3 },
				MatrixOrder.COLMAJOR);

		// Result matrices
		ArrayList<RealMatrix> resultMatrices = new ArrayList<RealMatrix>();

		for (BLAS b : blasList) {

			RealMatrix y = new RealMatrix(n, 1, new double[] { 5, 4, 3 },
					MatrixOrder.COLMAJOR);
			try {
				b.daxpy(1.0, x, y, 1);
			} finally {
				if (writeOutput) {
					System.out.println(newline + newline + "========== DAXPY (x" + 1 + ") "
							+ b.toString() + " ==========" + newline);
					System.out.println("x:" + newline + x + newline);
					System.out.println("y:" + newline + y + newline);
				}
				resultMatrices.add(y);
			}
		}

		return RealMatrix.mEquals(resultMatrices);
	}

	// Test if dgemv functions give correct results
	//		   1
	// 1,2,3 * 2 = 14
	// 4,5,6   3   32
	public static boolean dgemvTest(ArrayList<BLAS> blasList,
			boolean writeOutput) {
		
		String newline = System.getProperty("line.separator");

		int m = 2, n = 3;

		// Matrix A
		RealMatrix A = new RealMatrix(m, n, new double[] { 1, 4, 2, 5, 3, 6 },
				MatrixOrder.COLMAJOR);

		// Matrix A transposed
		RealMatrix TA = A.copy();
		TA.Transpose();

		RealMatrix x = new RealMatrix(n, 1, new double[] { 1, 2, 3 },
				MatrixOrder.COLMAJOR);

		// Result matrices
		ArrayList<RealMatrix> resultMatrices = new ArrayList<RealMatrix>();

		for (BLAS b : blasList) {

			RealMatrix y = new RealMatrix(m, 1, MatrixOrder.COLMAJOR);
			RealMatrix yTA = new RealMatrix(m, 1, MatrixOrder.COLMAJOR);
			try {
				b.dgemv(Transpose.NOTRANSPOSE, 1.0, A, x, 0.0, y, 1);
				b.dgemv(Transpose.TRANSPOSE, 1.0, TA, x, 0.0, yTA, 1);
			} finally {
				if (writeOutput) {
					System.out.println(newline + newline + "========== DGEMV (x" + 1 + ") "
							+ b.toString() + " ==========" + newline);
					System.out.println("A:" + newline + A + newline);
					System.out.println("x:" + newline + x + newline);
					System.out.println("y:" + newline + y + newline);
					System.out.println("yTA:" + newline + yTA + newline);
				}
				resultMatrices.add(y);
				resultMatrices.add(yTA);
			}
		}

		return RealMatrix.mEquals(resultMatrices);
	}

	// Test if dgemm functions give correct results
	//         1,2,3,4
	// 1,2,3 * 5,6,7,8 = 38, 14, 20, 26
	// 4,5,6   9,0,1,2   83, 38, 53, 68
	public static boolean dgemmTest(ArrayList<BLAS> blasList,
			boolean writeOutput) {

		String newline = System.getProperty("line.separator");
		
		int m = 2, n = 4, k = 3;

		// Matrix A
		RealMatrix A = new RealMatrix(m, k, new double[] { 1, 4, 2, 5, 3, 6 },
				MatrixOrder.COLMAJOR);

		// Matrix A transposed
		RealMatrix TA = A.copy();
		TA.Transpose();

		// Matrix B
		RealMatrix B = new RealMatrix(k, n, new double[] { 1, 5, 9, 2, 6, 0, 3,
				7, 1, 4, 8, 2 }, MatrixOrder.COLMAJOR);

		// Matrix B transposed
		RealMatrix TB = B.copy();
		TB.Transpose();

		// Result matrices
		ArrayList<RealMatrix> resultMatrices = new ArrayList<RealMatrix>();

		for (BLAS b : blasList) {

			RealMatrix C = new RealMatrix(m, n, MatrixOrder.COLMAJOR);
			RealMatrix CTA = new RealMatrix(m, n, MatrixOrder.COLMAJOR);
			RealMatrix CTB = new RealMatrix(m, n, MatrixOrder.COLMAJOR);
			RealMatrix CTATB = new RealMatrix(m, n, MatrixOrder.COLMAJOR);
			try {
				b.dgemm(Transpose.NOTRANSPOSE, Transpose.NOTRANSPOSE, 1.0, A,
						B, 0.0, C, 1);
				b.dgemm(Transpose.TRANSPOSE, Transpose.NOTRANSPOSE, 1.0, TA, B,
						0.0, CTA, 1);
				b.dgemm(Transpose.NOTRANSPOSE, Transpose.TRANSPOSE, 1.0, A, TB,
						0.0, CTB, 1);
				b.dgemm(Transpose.TRANSPOSE, Transpose.TRANSPOSE, 1.0, TA, TB,
						0.0, CTATB, 1);
			} finally {
				if (writeOutput) {
					System.out.println(newline + newline + "========== DGEMM (x" + 1 + ") "
							+ b.toString() + " ==========" + newline);
					System.out.println("A:" + newline + A + newline);
					System.out.println("B:" + newline + B + newline);
					System.out.println("C:" + newline + C + newline);
					System.out.println("CTA:" + newline + CTA + newline);
					System.out.println("CTB:" + newline + CTB + newline);
					System.out.println("CTATB:" + newline + CTATB + newline);
				}
				resultMatrices.add(C);
				resultMatrices.add(CTA);
				resultMatrices.add(CTB);
				resultMatrices.add(CTATB);
			}
		}

		return RealMatrix.mEquals(resultMatrices);
	}

	public static boolean covTest(ArrayList<BLAS> blasList, RealMatrix A,
			RealMatrix weightVector, RealMatrix referenceMtx,
			boolean writeOutput) {
		
		String newline = System.getProperty("line.separator");

		ArrayList<RealMatrix> resultMatrices = new ArrayList<RealMatrix>();

		for (BLAS b : blasList) {
			RealMatrix resMtx = new RealMatrix(A.columns(), A.columns(),
					MatrixOrder.COLMAJOR);
			try {
				b.covMtx(A, weightVector, resMtx, 1);
			} finally {
				if (writeOutput) {
					System.out.println(newline + newline + "========== COVTEST (x" + 1 + ") "
							+ b.toString() + " ==========" + newline);
					System.out.println("Covariance matrix:" + newline + resMtx + newline);
				}
				resultMatrices.add(resMtx);
			}
		}

		resultMatrices.add(referenceMtx);
		return RealMatrix.mEquals(resultMatrices);
	}

	public static boolean covPrimitiveTest(
			ArrayList<BLAS> blasList, RealMatrix A, RealMatrix weightVector,
			RealMatrix referenceMtx, boolean writeOutput) {
		
		String newline = System.getProperty("line.separator");

		ArrayList<RealMatrix> resultMatrices = new ArrayList<RealMatrix>();

		for (BLAS b : blasList) {
			RealMatrix ACopy = A.copy();
			RealMatrix resMtx = new RealMatrix(A.columns(), A.columns(),
					MatrixOrder.COLMAJOR);
			try {
				b.covMtxPrimitive(ACopy, weightVector, resMtx, 1);
			} finally {
				if (writeOutput) {
					System.out.println(newline + newline + "========== PRIMITIVE COVTEST (x" + 1 + ") "
							+ b.toString() + " ==========" + newline);
					System.out.println("Covariance matrix:" + newline + resMtx + newline);
				}
				resultMatrices.add(resMtx);
			}
		}

		resultMatrices.add(referenceMtx);
		return RealMatrix.mEquals(resultMatrices);
	}
}
