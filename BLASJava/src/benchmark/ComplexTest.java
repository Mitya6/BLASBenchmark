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
			int kGrowth, int repeatGrowth, int maxsize, int maxcount,
			boolean exponentialGrowth) {

		if (mGrowth < 0 || nGrowth < 0 || kGrowth < 0 || repeatGrowth < 0) {
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

			if (exponentialGrowth) {
				m *= mGrowth;
				n *= nGrowth;
				k *= kGrowth;
				repeatCount *= repeatGrowth;
			} else {
				m += mGrowth;
				n += nGrowth;
				k += kGrowth;
				repeatCount += repeatGrowth;
			}
			
		}

		printResultsToFile(blasList, BlasMethod.DGEMM, progressiveResults);
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

		printResultsToFile(blasList, BlasMethod.COV, progressiveResults);
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

		printResultsToFile(blasList, BlasMethod.COVPRIMITIVE,
				progressiveResults);
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

		printResultsToFile(blasList, BlasMethod.COV, progressiveResults);
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

		printResultsToFile(blasList, BlasMethod.COVPRIMITIVE,
				progressiveResults);
	}
	
	public static void covarianceTimeTest(ArrayList<BLAS> blasList,
			int repeatCount) {
		
		RealMatrix A = CovarianceHelper.getAMatrix();

		ArrayList<TestResult> progressiveResults = new ArrayList<TestResult>();

		System.out.println("Progressive test started.");
		
		for (int i = 2; i <= 32; i += 2) {
			
			RealMatrix Acopy = A.getFirstColumns(i);
			
			System.out.println("Calculating: cov size = " + i);

			progressiveResults.addAll(TimeTest.covTest(blasList, Acopy, -1, -1,
					repeatCount));
		}

		printResultsToFile(blasList, BlasMethod.COV, progressiveResults);
	}

	private static void printResults(ArrayList<BLAS> blasList,
			BlasMethod method, ArrayList<TestResult> results) {

		String newline = System.getProperty("line.separator");

		String s = newline + "========== " + method.toString() + " ==========";

		for (BLAS b : blasList) {
			s += newline + newline + b.toString();
			for (TestResult result : results) {
				if (result.getBlasType() == b.blasType) {
					s += result.toString();
				}
			}
		}

		System.out.println(s);
	}

	private static void printResultsToFile(ArrayList<BLAS> blasList,
			BlasMethod method, ArrayList<TestResult> results) {

		System.setOut(Benchmark.outputFile("result.csv"));

		String newline = System.getProperty("line.separator");

		String s = "";

		for (BLAS b : blasList) {
			s += b.toString() + newline;
			String sn = "n;";
			String sm = "m;";
			String sk = "k;";
			String srepeat = "repeat;";
			String sseconds = "seconds;";
			String snanotime = "nanotime;";
			for (TestResult result : results) {
				if (result.getBlasType() == b.blasType) {
					sn += result.getN() + ";";
					sm += result.getM() + ";";
					sk += result.getK() + ";";
					srepeat += "x" + result.getRepeatCount() + ";";
					sseconds += result.getSeconds() + ";";
					snanotime += result.getNanoseconds() + ";";
				}
			}
			s += sn + newline;
			s += sm + newline;
			s += sk + newline;
			s += srepeat + newline;
			s += sseconds + newline;
			s += snanotime + newline;
			s += newline;
		}

		System.out.println(s);
	}

}
