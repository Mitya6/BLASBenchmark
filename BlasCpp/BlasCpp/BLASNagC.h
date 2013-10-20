#ifndef BLASNAGC_H
#define BLASNAGC_H

//#include "BLASCpp.h"

namespace BLAS
{
	class BLASNagC //: public BLASCpp
	{
	public:

		// BLAS functions

		// daxpy: y <- alpha*x + y (intelMKL only)
		//void daxpy(int n, double alpha, double x[], int incx,
		//	double y[], int incy);

		// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
		void daxpby(int n, double alpha, double x[], int incx,
			double beta, double y[], int incy);

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

#endif // BLASNAGC_H