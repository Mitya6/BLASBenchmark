#include "BLASNagC.h"

using namespace BLAS;

//void BLASNagC::daxpy(int n, double alpha, double x[], int incx,
//					 double y[], int incy)
//{
//
//}

void BLASNagC::daxpby(int n, double alpha, double x[], int incx,
					  double beta, double y[], int incy)
{
	// return test data
	for (int i = 0; i < n; i++)
	{
		y[i] = -1.0;
	}
}

void BLASNagC::dgemv(int order, int transA, int m, int n, double alpha,
					 double A[], int lda, double x[], int incx, 
					 double beta, double y[], int incy)
{

}

void BLASNagC::dgemm(int order, int transA, int transB, int m, int n, int k,
					 double alpha, double A[], int lda, double B[], int ldb,
					 double beta, double C[], int ldc)
{

}