package blas;

public abstract class BLAS {

	public final BlasType blasType;

	protected BLAS(BlasType blasType) {
		this.blasType = blasType;
	}

	// BLAS functions

	// daxpy: y <- alpha*x + y (intelMKL only)
	// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
	public abstract long daxpy(double alpha, RealMatrix x, RealMatrix y,
			int times);

	// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
	public abstract long dgemv(Transpose transA, double alpha, RealMatrix A,
			RealMatrix x, double beta, RealMatrix y, int times);

	// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
	public abstract long dgemm(Transpose transA, Transpose transB,
			double alpha, RealMatrix A, RealMatrix B, double beta,
			RealMatrix C, int times);

	// Covariance functions

	// Covariance Matrix function call if supported otherwise calls primitive
	public long covMtx(RealMatrix A, RealMatrix weightVector,
			RealMatrix varCovarMtx, int times) {
		return covMtxPrimitive(A, weightVector, varCovarMtx, times);
	}

	// Covariance Matrix based on BLAS primitive functions
	public final long covMtxPrimitive(RealMatrix A, RealMatrix weightVector,
			RealMatrix varCovarMtx, int times) {

		long startTime = System.nanoTime();

		double[] Adata = A.getData().getData();

		// 3) weighted average for each column
		RealMatrix weightAvgVector = new RealMatrix(1, A.columns(),
						new double[A.columns()], MatrixOrder.COLMAJOR);

		dgemm(Transpose.TRANSPOSE, Transpose.NOTRANSPOSE, 1.0 / A.rows(),
				weightVector, A, 1.0, weightAvgVector, 1);
		
		double[] weightedAvgs = weightAvgVector.getData().getData();

		// 4) centered data => m(i,j) - weightedAvg(j)
		// dgemm: C <- alpha*op(A)*op(B) + beta*C
		// 1*A*I + 1*weightMtx
		double[] centeredData = new double[A.rows() * A.columns()];
		for (int j = 0; j < A.columns(); j++) {
			for (int i = 0; i < A.rows(); i++) {
				centeredData[j * A.rows() + i] = Adata[j * A.rows() + i]
						- weightedAvgs[j];
			}
		}

		// 5) weighted centered data => centered data(i,j)*weights(i)
		double[] weights = weightVector.getData().getData();
		double[] weightedCenteredData = new double[A.rows() * A.columns()];
		for (int j = 0; j < A.columns(); j++) {
			for (int i = 0; i < A.rows(); i++) {
				weightedCenteredData[j * A.rows() + i] = centeredData[j
						* A.rows() + i]
						* weights[i];
			}
		}

		// 6) calculate cov mtx
		dgemm(Transpose.TRANSPOSE, Transpose.NOTRANSPOSE, 1.0 / (A.rows() - 1),
				new RealMatrix(A.rows(), A.columns(), centeredData,
						MatrixOrder.COLMAJOR),
				new RealMatrix(A.rows(), A.columns(), weightedCenteredData,
						MatrixOrder.COLMAJOR), 0.0, varCovarMtx, 1);

		long elapsedTime = System.nanoTime() - startTime;

		return elapsedTime;

	}

	@Override
	public abstract String toString();
}
