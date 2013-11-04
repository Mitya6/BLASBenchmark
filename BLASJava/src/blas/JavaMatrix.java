package blas;

import java.text.DecimalFormat;
import java.util.Random;

public class JavaMatrix {

	private int m;
	private int n;
	public double[] data;

	private MatrixOrder order;

	public JavaMatrix(int m, int n, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = new double[m * n];
		this.order = order;
	}

	public JavaMatrix(int m, int n, double[] data, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = data;
		this.order = order;
	}

	public static JavaMatrix createRandomDouble01(int m, int n,
			MatrixOrder order) {
		JavaMatrix matrix = new JavaMatrix(m, n, order);
		Random r = new Random();
		for (int i = 0; i < matrix.m * matrix.n; i++) {
			matrix.data[i] = r.nextDouble();
		}
		return matrix;
	}

	/*public static JavaMatrix createRandomInt10(int m, int n, MatrixOrder order) {
		JavaMatrix matrix = new JavaMatrix(m, n, order);
		Random r = new Random();
		for (int i = 0; i < matrix.m * matrix.n; i++) {
			matrix.data[i] = (int) (r.nextDouble() * 10);
		}
		return matrix;
	}*/

	public JavaMatrix copy() {
		JavaMatrix copyMtx = new JavaMatrix(this.m, this.n, this.order);
		for (int i = 0; i < this.m * this.n; i++) {
			copyMtx.data[i] = this.data[i];
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
			return this.data[i * this.n + j];
		} else {
			return this.data[j * this.m + i];
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
