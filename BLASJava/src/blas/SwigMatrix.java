package blas;

import java.text.DecimalFormat;
import java.util.Random;

import nativeInterface.BLASCppModule;
import nativeInterface.SWIGTYPE_p_double;

public class SwigMatrix {
	private int m;
	private int n;
	public SWIGTYPE_p_double data;

	private MatrixOrder order;

	public SwigMatrix(int m, int n, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = BLASCppModule.new_doubleArray(n * m);
		this.order = order;
	}

	public SwigMatrix(int m, int n, SWIGTYPE_p_double data, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = data;
		this.order = order;
	}

	public static SwigMatrix createRandomDouble01(int m, int n,
			MatrixOrder order) {
		SwigMatrix matrix = new SwigMatrix(m, n, order);
		Random r = new Random();
		for (int i = 0; i < matrix.m * matrix.n; i++) {
			BLASCppModule.doubleArray_setitem(matrix.data, i, r.nextDouble());
		}
		return matrix;
	}

	/*public static SwigMatrix createRandomInt10(int m, int n, MatrixOrder order) {
		SwigMatrix matrix = new SwigMatrix(m, n, order);
		Random r = new Random();
		for (int i = 0; i < matrix.m * matrix.n; i++) {
			BLASCppModule.doubleArray_setitem(matrix.data, i,
					(int) (r.nextDouble() * 10));
		}
		return matrix;
	}*/

	public SwigMatrix copy() {
		SwigMatrix copyMtx = new SwigMatrix(this.m, this.n, this.order);
		for (int i = 0; i < this.m * this.n; i++) {
			BLASCppModule.doubleArray_setitem(copyMtx.data, i,
					BLASCppModule.doubleArray_getitem(this.data, i));
		}
		return copyMtx;
	}

	@Override
	public String toString() {
		String s = "[";
		DecimalFormat df = new DecimalFormat("#.######");

		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < this.n; j++) {
				s += df.format(get(i, j));
				if (j < this.n - 1) {
					s += ";";
				}
			}
			if (i < this.m - 1) {
				s += "\n";
			}
		}
		return s + "]";
	}

	public double get(int i, int j) {
		if (i < 0 || j < 0 || i >= this.m || j >= this.n) {
			throw new IndexOutOfBoundsException();
		}
		if (this.order == MatrixOrder.RowMajor) {
			return BLASCppModule.doubleArray_getitem(this.data, i * this.n + j);
		} else {
			return BLASCppModule.doubleArray_getitem(this.data, j * this.m + i);
		}
	}

	public int rows() {
		return this.m;
	}

	public int columns() {
		return this.n;
	}

	public MatrixOrder order() {
		return this.order;
	}
}
