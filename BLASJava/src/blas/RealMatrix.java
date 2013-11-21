package blas;

import java.text.DecimalFormat;
import java.util.Random;

public class RealMatrix {
	private int m;
	private int n;
	public RealArray data;

	private MatrixOrder order;

	public RealMatrix(int m, int n, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = new RealArray(new double[m * n]);
		this.order = order;
	}

	public RealMatrix(int m, int n, double[] data, MatrixOrder order) {
		this.m = m;
		this.n = n;
		this.data = new RealArray(data);
		this.order = order;
	}

	public static RealMatrix createRandomDouble01(int m, int n,
			MatrixOrder order) {
		double[] data = new double[n * m];
		Random r = new Random();
		for (int i = 0; i < m * n; i++) {
			data[i] = r.nextDouble();
		}
		return new RealMatrix(m, n, data, order);
	}

	public RealMatrix copy() {
		double[] originalData = this.data.toDouble();
		double[] copyData = new double[this.n * this.m];
		for (int i = 0; i < this.m * this.n; i++) {
			copyData[i] = originalData[i];
		}
		return new RealMatrix(this.m, this.n, copyData, this.order);
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

	public void setOrder(MatrixOrder newOrder) {
		if (newOrder == this.order) {
			return;
		}
		if (newOrder == MatrixOrder.RowMajor) {
			changeToRowMajor();
		}
		if (newOrder == MatrixOrder.ColMajor) {
			changeToColMajor();
		}
	}

	private void changeToRowMajor() {
		if (this.order != MatrixOrder.ColMajor) {
			return;
		}
		
		double[] newdata = new double[m * n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				newdata[i * n + j] = get(i, j);
			}
		}

		this.order = MatrixOrder.RowMajor;
		this.data = new RealArray(newdata);
	}

	private void changeToColMajor() {
		if (this.order != MatrixOrder.RowMajor) {
			return;
		}

		double[] newdata = new double[m * n];

		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				newdata[j * m + i] = get(i, j);
			}
		}

		this.order = MatrixOrder.ColMajor;
		this.data = new RealArray(newdata);
	}

	public double get(int i, int j) {
		if (i < 0 || j < 0 || i >= this.m || j >= this.n) {
			throw new IndexOutOfBoundsException();
		}
		if (this.order == MatrixOrder.RowMajor) {
			return this.data.get(i * this.n + j);
		} else {
			return this.data.get(j * this.m + i);
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
