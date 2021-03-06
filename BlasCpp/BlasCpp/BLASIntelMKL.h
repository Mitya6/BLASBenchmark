#ifndef BLASINTELMKL_H
#define BLASINTELMKL_H

//#include "BLASCpp.h"

namespace BLAS
{
	class BLASIntelMKL
	{
	public:

		// BLAS functions

		// daxpy: y <- alpha*x + y (intelMKL only)
		void daxpy(int n, double alpha, double x[], int incx,
			double y[], int incy);

		// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
		void dgemv(int order, int transA, int m, int n, double alpha,
			double A[], int lda, double x[], int incx, 
			double beta, double y[], int incy);

		// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
		void dgemm(int order, int transA, int transB, int m, int n, int k,
			double alpha, double A[], int lda, double B[], int ldb,
			double beta, double C[], int ldc);
	};
}

#endif // BLASINTELMKL_H