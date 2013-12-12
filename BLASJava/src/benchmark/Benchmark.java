package benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import blas.BLAS;
import blas.IntelMKLBLAS;
import blas.JBlasBLAS;
import blas.NagCBLAS;
import blas.NagJBLAS;

public class Benchmark {

	private static ArrayList<BLAS> blasList;

	public static void main(String[] args) {
		

		blasList = new ArrayList<BLAS>();
		blasList.add(new JBlasBLAS());
		blasList.add(new IntelMKLBLAS());
		blasList.add(new NagCBLAS());
		blasList.add(new NagJBLAS());

		ComplexTest.correctnessTests(blasList, true);
		
		

		// Everything grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 3, 4, 5, 1, 2, 2, 2, 2, 1000, 1000, true);

		// Matrix size grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 2, 2, 2, 1, 2, 2, 2, 1, 2048, 2, true);

		// Repeat count grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 100, 100, 100, 1, 1, 1, 1, 2, 200, 2048, true);

		//ComplexTest.progressivePreparedCovTimeTest(blasList, 1, 2, 2048);

		//ComplexTest.progressivePreparedCovPrimitiveTimeTest(blasList, 1, 2, 2048);

		//ComplexTest.progressiveRandomCovTimeTest(blasList, 220, 2, 32, 1, 2, 1, 2048, 32);

		//ComplexTest.progressiveRandomCovPrimitiveTimeTest(blasList, 2, 1, 1, 2, 2, 1, 2048, 2);
		
		
		// Performance tests
		
		// 345 linear
		//ComplexTest.progressiveDgemmTimeTest(blasList, 3, 5, 4, 8, 60, 100, 80, 0, 1500, 8, false);
		
		// 444 linear
		//ComplexTest.progressiveDgemmTimeTest(blasList, 4, 4, 4, 8, 80, 80, 80, 0, 1200, 8, false);
		
		// Covariance prepared test
		//ComplexTest.covarianceTimeTest(blasList, 32);
		
		// Covariance random test (exponential)
		//ComplexTest.progressiveRandomCovTimeTest(blasList, 220, 2, 32, 1, 2, 1, 2048, 32);
		

	}

	public static PrintStream outputFile(String name) {
		PrintStream ps = null;
		try {
			ps = new PrintStream(new File(name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ps;
	}

	static {
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\OnlabII\\BLASBenchmark\\BLASJava\\nag_jni.dll");
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\OnlabII\\BLASBenchmark\\BlasCpp\\x64\\Debug\\BlasCpp.dll");
	}

}
