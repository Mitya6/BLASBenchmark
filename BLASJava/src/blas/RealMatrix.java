package blas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class RealMatrix {
	private int m;
	private int n;
	private RealArray data;
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

	public static RealMatrix createIdentity(int m, MatrixOrder order) {
		double[] data = new double[m * m];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (i == j) {
					data[i * m + j] = 1.0;
				} else {
					data[i * m + j] = 0.0;
				}
			}
		}
		return new RealMatrix(m, m, data, order);
	}

	public RealMatrix copy() {
		double[] originalData = this.data.getData();
		double[] copyData = new double[this.n * this.m];
		for (int i = 0; i < this.m * this.n; i++) {
			copyData[i] = originalData[i];
		}
		return new RealMatrix(this.m, this.n, copyData, this.order);
	}

	public void Transpose() {

		MatrixOrder originalOrder = this.order;

		// Use JBlasBLAS dgemm method for transposing
		JBlasBLAS jbb = new JBlasBLAS();
		RealMatrix copyMtx = this.copy();
		RealMatrix identity = createIdentity(rows(), MatrixOrder.COLMAJOR);
		setOrder(MatrixOrder.COLMAJOR);

		this.data.init(0.0);
		int tmp = this.m;
		this.m = this.n;
		this.n = tmp;

		jbb.dgemm(Transpose.TRANSPOSE, Transpose.NOTRANSPOSE, 1.0, copyMtx,
				identity, 0.0, this, 1);

		this.setOrder(originalOrder);
	}

	public boolean mEquals(RealMatrix other) {
		return this.data.equals(other.getData());
	}

	public static boolean mEquals(ArrayList<RealMatrix> matrices) {
		for (int i = 0; i < matrices.size(); i++) {
			if (i > 0 && matrices.get(i).mEquals(matrices.get(0)) == false) {
				return false;
			}
		}
		return true;
	}

	public void swapColumns() {
		if (n < 2) {
			return;
		}
		double[] secondColumn = new double[this.m];
		for (int i = 0; i < m; i++) {
			secondColumn[i] = this.data.get(m + i);
		}
		for (int i = 0; i < m; i++) {
			this.data.set(m+i, this.data.get(i));
			this.data.set(i, secondColumn[i]);
		}
	}

	@Override
	public String toString() {
		
		String newline = System.getProperty("line.separator");
		
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
				s += newline;
			}
		}
		return s + "]";
	}

	private void changeToRowMajor() {
		if (this.order != MatrixOrder.COLMAJOR) {
			return;
		}

		double[] newdata = new double[m * n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				newdata[i * n + j] = get(i, j);
			}
		}

		this.order = MatrixOrder.ROWMAJOR;
		this.data = new RealArray(newdata);
	}

	private void changeToColMajor() {
		if (this.order != MatrixOrder.ROWMAJOR) {
			return;
		}

		double[] newdata = new double[m * n];

		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				newdata[j * m + i] = get(i, j);
			}
		}

		this.order = MatrixOrder.COLMAJOR;
		this.data = new RealArray(newdata);
	}

	public double get(int i, int j) {
		if (i < 0 || j < 0 || i >= this.m || j >= this.n) {
			throw new IndexOutOfBoundsException();
		}
		if (this.order == MatrixOrder.ROWMAJOR) {
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

	public void setOrder(MatrixOrder newOrder) {
		if (newOrder == this.order) {
			return;
		}
		if (newOrder == MatrixOrder.ROWMAJOR) {
			changeToRowMajor();
		}
		if (newOrder == MatrixOrder.COLMAJOR) {
			changeToColMajor();
		}
	}

	public MatrixOrder getOrder() {
		return this.order;
	}

	public void setData(RealArray data) {
		this.data = data;
	}

	public RealArray getData() {
		return this.data;
	}

	public RealMatrix getFirstColumns(int number) {
		if (this.order == MatrixOrder.ROWMAJOR) {
			setOrder(MatrixOrder.COLMAJOR);
		}
		
		double[] newdata = new double[this.m * number];
		for (int i = 0; i < this.m * number; i++) {
			newdata[i] = this.getData().get(i);
		}
		
		return new RealMatrix(m, number, newdata, MatrixOrder.COLMAJOR);
	}
}