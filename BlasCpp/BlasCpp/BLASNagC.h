#ifndef BLASNAGC_H
#define BLASNAGC_H

//#include "BLASCpp.h"

namespace BLAS
{
	class BLASNagC //: public BLASCpp
	{
	public:

		// BLAS functions

		// daxpby: y <- alpha*x + beta*y (NagC + intelMKL)
		void daxpby(int n, double alpha, double x[], int incx,
			double beta, double y[], int incy);

		// dgemv: y <- alpha*op(A)*x + beta*y (NagC + intelMKL)
		void dgemv(int order, int transA, int m, int n, double alpha,
			double A[], int pda, double x[], int incx, 
			double beta, double y[], int incy);

		// dgemm: C <- alpha*op(A)*op(B) + beta*C (NagC + intelMKL)
		void dgemm(int order, int transA, int transB, int m, int n, int k,
			double alpha, double A[], int pda, double B[], int pdb,
			double beta, double C[], int pdc);

		//
		void nagcorrcov(int n, int m, double x[], int tdx, int sx[], 
			double wt[], double *sw, double wmean[], double std[], 
			double r[], int tdr, double v[], int tdv);
	};
}

#endif // BLASNAGC_H