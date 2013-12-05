package benchmark;

import java.util.ArrayList;

import blas.BLAS;
import blas.BlasMethod;
import blas.RealMatrix;

public class ComplexTest {

	public static void correctnessTests(ArrayList<BLAS> blasList,
			boolean writeDetailedOutput) {

		boolean correctResults = true;
		correctResults = correctResults
				&& CorrectnessTest.daxpyTest(blasList, writeDetailedOutput);
		correctResults = correctResults
				&& CorrectnessTest.dgemvTest(blasList, writeDetailedOutput);
		correctResults = correctResults
				&& CorrectnessTest.dgemmTest(blasList, writeDetailedOutput);
		correctResults = correctResults
				&& CorrectnessTest.covTest(blasList,
						CovarianceHelper.getAMatrix(),
						CovarianceHelper.getWeigthVector(220),
						CovarianceHelper.getReferenceMatrix(),
						writeDetailedOutput);
		correctResults = correctResults
				&& CorrectnessTest.covPrimitiveTest(blasList,
						CovarianceHelper.getAMatrix(),
						CovarianceHelper.getWeigthVector(220),
						CovarianceHelper.getReferenceMatrix(),
						writeDetailedOutput);
		System.out.println(correctResults);
	}

	public static void progressiveDgemmTimeTest(ArrayList<BLAS> blasList,
			int m, int n, int k, int repeatCount, int mGrowth, int nGrowth,
			int kGrowth, int repeatGrowth, int maxsize, int maxcount) {

		if (mGrowth < 1 || nGrowth < 1 || kGrowth < 1 || repeatGrowth < 1) {
			return;
		}

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");

		while (m <= maxsize && n <= maxsize && k <= maxsize
				&& repeatCount <= maxcount) {

			System.out.println("Calculating: m = " + m + ", n = " + n
					+ ", k = " + k + ", x" + repeatCount);

			progressiveResults.addAll(TimeTest.dgemmTest(blasList, m, n, k,
					repeatCount));

			m *= mGrowth;
			n *= nGrowth;
			k *= kGrowth;
			repeatCount *= repeatGrowth;
		}

		printResults(blasList, BlasMethod.DGEMM, progressiveResults);
	}

	public static void progressiveRandomCovTimeTest(ArrayList<BLAS> blasList,
			int obs, int vars, int repeatCount, int obsGrowth, int varsGrowth,
			int repeatGrowth, int maxsize, int maxcount) {

		if (obsGrowth < 1 || varsGrowth < 1 || repeatGrowth < 1) {
			return;
		}

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");

		while (obs <= maxsize && vars <= maxsize && repeatCount <= maxcount) {

			System.out.println("Calculating: m = " + obs + ", n = " + vars
					+ ", x" + repeatCount);

			progressiveResults.addAll(TimeTest.covTest(blasList, null, obs,
					vars, repeatCount));

			obs *= obsGrowth;
			vars *= varsGrowth;
			repeatCount *= repeatGrowth;
		}

		printResults(blasList, BlasMethod.COV, progressiveResults);
	}

	public static void progressiveRandomCovPrimitiveTimeTest(
			ArrayList<BLAS> blasList, int obs, int vars, int repeatCount,
			int obsGrowth, int varsGrowth, int repeatGrowth, int maxsize,
			int maxcount) {
		if (obsGrowth < 1 || varsGrowth < 1 || repeatGrowth < 1) {
			return;
		}

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");

		while (obs <= maxsize && vars <= maxsize && repeatCount <= maxcount) {

			System.out.println("Calculating: m = " + obs + ", n = " + vars
					+ ", x" + repeatCount);

			progressiveResults.addAll(TimeTest.covPrimitiveTest(blasList, null,
					obs, vars, repeatCount));

			obs *= obsGrowth;
			vars *= varsGrowth;
			repeatCount *= repeatGrowth;
		}

		printResults(blasList, BlasMethod.COVPRIMITIVE, progressiveResults);
	}

	public static void progressivePreparedCovTimeTest(ArrayList<BLAS> blasList,
			int repeatCount, int repeatGrowth, int maxcount) {
		if (repeatGrowth < 1) {
			return;
		}
		
		RealMatrix A = CovarianceHelper.getAMatrix();

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");

		while (repeatCount <= maxcount) {

			System.out.println("Calculating: x" + repeatCount);

			progressiveResults.addAll(TimeTest.covTest(blasList, A, -1, -1,
					repeatCount));

			repeatCount *= repeatGrowth;
		}

		printResults(blasList, BlasMethod.COV, progressiveResults);
	}

	public static void progressivePreparedCovPrimitiveTimeTest(
			ArrayList<BLAS> blasList, int repeatCount, int repeatGrowth,
			int maxcount) {
		if (repeatGrowth < 1) {
			return;
		}
		
		RealMatrix A = CovarianceHelper.getAMatrix();

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");

		while (repeatCount <= maxcount) {

			System.out.println("Calculating: x" + repeatCount);

			progressiveResults.addAll(TimeTest.covPrimitiveTest(blasList, A,
					-1, -1, repeatCount));

			repeatCount *= repeatGrowth;
		}

		printResults(blasList, BlasMethod.COVPRIMITIVE, progressiveResults);
	}

	private static void printResults(ArrayList<BLAS> blasList,
			BlasMethod method, ArrayList<TestResult> results) {

		String s = "\n========== " + method.toString() + " ==========";

		for (BLAS b : blasList) {
			s += "\n\n" + b.toString();
			for (TestResult result : results) {
				if (result.getBlasType() == b.blasType) {
					s += result.toString();
				}
			}
		}

		System.out.println(s);
	}

}
