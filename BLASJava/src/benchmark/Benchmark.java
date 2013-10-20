package benchmark;

import org.jblas.DoubleMatrix;
import org.jblas.NativeBlas;

import blas.BLAS;
import blas.NagCBLAS;

public class Benchmark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LoadLibraries();
		
		jblasTest();
		
		nagcTest();
	}

	public static void jblasTest() {
		int n = 5;
		double alpha = 2;

		DoubleMatrix x = new DoubleMatrix(n, 1, 1, 2, 3, 4, 5);
		DoubleMatrix y = new DoubleMatrix(n, 1, 1, 2, 3, 4, 5);

		x.print();
		y.print();

		NativeBlas.daxpy(n, alpha, x.data, 0, 1, y.data, 0, 1);

		y.print();

	}

	public static void nagcTest() {
		BLAS b = new NagCBLAS();
		
		double[] y = new double[6];
		
		for (int i = 0; i < y.length; i++) {
			System.out.println(y[i]);
		}
		
		b.daxpy(6, 1, new double[6], 1, y, 1);
				
		for (int i = 0; i < y.length; i++) {
			System.out.println(y[i]);
		}
	}
	
	public static void LoadLibraries() {
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\ÖnlabII\\BLASBenchmark\\BlasCpp\\x64\\Debug\\BlasCpp.dll");
		//"..\\..\\..\\BlasCpp\\x64\\Debug\\BlasCpp.dll"
	}

}
