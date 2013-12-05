package benchmark;

import java.text.DecimalFormat;

import blas.BlasMethod;
import blas.BlasType;

public class TestResult {
	private BlasType blasType;
	private BlasMethod method;
	private int repeatCount;
	private int m, n, k;
	private long nanoseconds;

	public TestResult(BlasType blasType, BlasMethod method, int repeatCount,
			int m, int n, int k, long nanoseconds) {
		this.blasType = blasType;
		this.method = method;
		this.repeatCount = repeatCount;
		this.m = m;
		this.n = n;
		this.k = k;
		this.nanoseconds = nanoseconds;
	}

	@Override
	public String toString() {
		String s = "\n";
		s += "m = " + m;
		s += ", n = " + n;
		s += ", k = " + k;
		s += ", x" + repeatCount;
		s += "\ntime = " + getSeconds() + " s (" + nanoseconds + " ns)\n";
		return s;
	}

	public BlasType getBlasType() {
		return blasType;
	}

	public BlasMethod getMethod() {
		return method;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

	public long getNanoseconds() {
		return nanoseconds;
	}

	public String getSeconds() {
		double seconds = nanoseconds / 1000000000.0;
		return new String(
		new DecimalFormat("#.####").format(seconds));
	}
}
