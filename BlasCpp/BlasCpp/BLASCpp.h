#ifndef BLASCPP_H
#define BLASCPP_H

namespace BLAS
{
	class BLASCpp
	{
	public:

		// BLAS functions

		// daxpy: y <- alpha*x + y (intelMKL only)
		virtual void daxpy(int n, double alpha, double x[], int incx,
			double y[], int incy);

		// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
		virtual void daxpby(int n, double alpha, double x[], int incx,
			double beta, double y[], int incy);

		// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
		virtual void dgemv(int order, int transA, int m, int n, double alpha,
			double A[], int lda, double x[], int incx, 
			double beta, double y[], int incy);

		// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
		virtual void dgemm(int order, int transA, int transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc);
	};
}

#endif // BLASCPP_H