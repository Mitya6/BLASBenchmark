#include "BLASNagC.h"
#include "nag.h"
#include "nagf16.h"
#include "nagg02.h"

using namespace BLAS;

void BLASNagC::daxpby(int n, double alpha, double x[], int incx, double beta, 
		double y[], int incy)
{
	NagError* fail = 0;
	f16ecc(n, alpha, x, incx, beta, y, incy, fail);
}

void BLASNagC::dgemv(int order, int transA, int m, int n, double alpha, double A[], 
		int pda, double x[], int incx, double beta, double y[], int incy)
{
	NagError* fail = 0;
	f16pac((Nag_OrderType)order, (Nag_TransType)transA, m, n, alpha, A,
		pda, x, incx, beta, y, incy, fail);
}

void BLASNagC::dgemm(int order, int transA, int transB, int m, int n, int k, double alpha,
		double A[], int pda, double B[], int pdb, double beta, double C[], int pdc)
{
	NagError* fail = 0;
	f16yac((Nag_OrderType)order, (Nag_TransType)transA, (Nag_TransType)transB,
		m, n, k, alpha, A, pda, B, pdb, beta, C, pdc, fail);
}

void BLASNagC::nagcorrcov(int n, int m, double x[], int tdx, int sx[], double wt[], 
		double *sw, double wmean[], double std[], double r[], int tdr, double v[], int tdv)
{
	NagError* fail = 0;
	g02bxc(n, m, x, tdx, sx, wt, sw, wmean, std, r, tdr, v, tdv, fail);
}