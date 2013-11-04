package blas;

import nativeInterface.BLASCppModule;

import org.junit.Test;

public class NagCBLASTest {

	static {
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\ÖnlabII\\BLASBenchmark\\BlasCpp\\Debug\\BlasCpp.dll");
	}

	private SwigMatrix swgmtx = null;

	@Test
	public void testMultiplyMatrix() {
		try {
			swgmtx = SwigMatrix.createRandomDouble01(5, 1, MatrixOrder.RowMajor);
			NagCBLAS ncb = new NagCBLAS();

			System.out.println("\n\n========== DAXPY(NagC) ==========\n");
			System.out.println("swigmatrix:\n" + swgmtx + "\n");

			ncb.daxpy(swgmtx.rows(), 32, BLASCppModule.new_doubleArray(1), 1,
					swgmtx.data, 1);
			
			// assert?
		} finally {
			System.out.println("swigmatrix:\n" + swgmtx + "\n");
		}
	}

}
