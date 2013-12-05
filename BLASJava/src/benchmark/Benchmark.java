package benchmark;

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

		

		//ComplexTest.correctnessTests(blasList, true);
		
		

		// Everything grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 3, 4, 5, 1, 2, 2, 2, 2, 1000, 1000);

		// Matrix size grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 2, 2, 2, 1, 2, 2, 2, 1, 2048, 2);

		// Repeat count grows
		//ComplexTest.progressiveDgemmTimeTest(blasList, 100, 100, 100, 1, 1, 1, 1, 2, 200, 2048);
		
		

		//ComplexTest.progressivePreparedCovTimeTest(blasList, 1, 2, 2048);

		//ComplexTest.progressivePreparedCovPrimitiveTimeTest(blasList, 1, 2, 2048);
		
		//ComplexTest.progressiveRandomCovTimeTest(blasList, 2, 1, 1, 2, 2, 1, 2048, 2);
		
		//ComplexTest.progressiveRandomCovPrimitiveTimeTest(blasList, 2, 1, 1, 2, 2, 1, 2048, 2);
		
	}

	static {
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\OnlabII\\BLASBenchmark\\BLASJava\\nag_jni.dll");
		System.load("C:\\Users\\Mitya\\Documents\\Egyetem\\MII\\OnlabII\\BLASBenchmark\\BlasCpp\\x64\\Debug\\BlasCpp.dll");
	}

}
