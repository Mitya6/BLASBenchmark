package blas;

import nativeInterface.BLASNagC;
import nativeInterface.swigDoubleArray;

public class NagCBLAS extends BLAS {

	private BLASNagC bnc;

	public NagCBLAS() {
		super(BlasType.NAGC);
		this.bnc = new BLASNagC();
	}

	@Override
	public long daxpy(double alpha, RealMatrix x, RealMatrix y, int times) {

		int n = x.rows();

		swigDoubleArray xSWIG = x.getData().toSwigDoubleArray();
		swigDoubleArray ySWIG = y.getData().toSwigDoubleArray();

		long startTime = System.nanoTime();

		bnc.daxpby(n, alpha, xSWIG.cast(), 1, 1.0, ySWIG.cast(), 1);

		long elapsedTime = System.nanoTime() - startTime;

		y.setData((new RealArray(ySWIG, y.getData().getLength())));
		return elapsedTime;
	}

	@Override
	public long dgemv(Transpose transA, double alpha, RealMatrix A,
			RealMatrix x, double beta, RealMatrix y, int times) {

		int NAG_ORDERTYPE = 102;
		int NAG_TRANSTYPE_A = transA == Transpose.NOTRANSPOSE ? 111 : 112;
		int m = A.rows();
		int n = A.columns();
		int pda = m;

		swigDoubleArray ASWIG = A.getData().toSwigDoubleArray();
		swigDoubleArray xSWIG = x.getData().toSwigDoubleArray();
		swigDoubleArray ySWIG = y.getData().toSwigDoubleArray();

		long startTime = System.nanoTime();

		bnc.dgemv(NAG_ORDERTYPE, NAG_TRANSTYPE_A, m, n, alpha, ASWIG.cast(),
				pda, xSWIG.cast(), 1, beta, ySWIG.cast(), 1);

		long elapsedTime = System.nanoTime() - startTime;

		y.setData((new RealArray(ySWIG, y.getData().getLength())));
		return elapsedTime;

	}

	@Override
	public long dgemm(Transpose transA, Transpose transB, double alpha,
			RealMatrix A, RealMatrix B, double beta, RealMatrix C, int times) {

		int NAG_ORDERTYPE = 102;
		int NAG_TRANSTYPE_A = transA == Transpose.NOTRANSPOSE ? 111 : 112;
		int NAG_TRANSTYPE_B = transB == Transpose.NOTRANSPOSE ? 111 : 112;
		int m = transA == Transpose.NOTRANSPOSE ? A.rows() : A.columns();
		int n = transB == Transpose.NOTRANSPOSE ? B.columns() : B.rows();
		int k = transA == Transpose.NOTRANSPOSE ? A.columns() : A.rows();
		int pda = transA == Transpose.NOTRANSPOSE ? m : k;
		int pdb = transB == Transpose.NOTRANSPOSE ? k : n;
		int pdc = m;

		swigDoubleArray ASWIG = A.getData().toSwigDoubleArray();
		swigDoubleArray BSWIG = B.getData().toSwigDoubleArray();
		swigDoubleArray CSWIG = C.getData().toSwigDoubleArray();

		long startTime = System.nanoTime();

		bnc.dgemm(NAG_ORDERTYPE, NAG_TRANSTYPE_A, NAG_TRANSTYPE_B, m, n, k,
				alpha, ASWIG.cast(), pda, BSWIG.cast(), pdb, beta,
				CSWIG.cast(), pdc);

		long elapsedTime = System.nanoTime() - startTime;

		C.setData((new RealArray(CSWIG, m * n)));
		return elapsedTime;
	}

	@Override
	public long covMtx(RealMatrix A, RealMatrix weightVector,
			RealMatrix varCovarMtx, int times) {

		A.setOrder(MatrixOrder.ROWMAJOR);
		weightVector.setOrder(MatrixOrder.ROWMAJOR);
		varCovarMtx.setOrder(MatrixOrder.ROWMAJOR);

		int n = A.rows();
		int m = A.columns();
		int tdx = m;
		int tdr = m;
		int tdv = m;

		swigDoubleArray ASWIG = A.getData().toSwigDoubleArray();
		swigDoubleArray weightSWIG = weightVector.getData().toSwigDoubleArray();
		swigDoubleArray varCovarSWIG = varCovarMtx.getData()
				.toSwigDoubleArray();
		swigDoubleArray wmeanSWIG = new swigDoubleArray(m);
		swigDoubleArray stdSWIG = new swigDoubleArray(m);

		long startTime = System.nanoTime();

		bnc.nagcorrcov(n, m, ASWIG.cast(), tdx, null, weightSWIG.cast(),
				(new swigDoubleArray(1)).cast(), wmeanSWIG.cast(),
				stdSWIG.cast(), (new swigDoubleArray(m * m)).cast(), tdr,
				varCovarSWIG.cast(), tdv);

		long elapsedTime = System.nanoTime() - startTime;

		varCovarMtx.setData((new RealArray(varCovarSWIG, m * m)));
		
		A.setOrder(MatrixOrder.COLMAJOR);
		weightVector.setOrder(MatrixOrder.COLMAJOR);
		varCovarMtx.setOrder(MatrixOrder.COLMAJOR);
		
		return elapsedTime;
	}

	@Override
	public String toString() {
		return "NagC_BLAS";
	}
}
