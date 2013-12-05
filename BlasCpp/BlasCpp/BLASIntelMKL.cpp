#include "BLASIntelMKL.h"
#include <iostream>
#include "mkl.h"

using namespace BLAS;

void BLASIntelMKL::daxpy(int n, double alpha, double x[], int incx,
						 double y[], int incy)
{
	cblas_daxpy(n, alpha, x, incx, y, incy);
}

void BLASIntelMKL::dgemv(int order, int transA, int m, int n, double alpha,
						 double A[], int lda, double x[], int incx, 
						 double beta, double y[], int incy)
{
	cblas_dgemv((CBLAS_ORDER)order, (CBLAS_TRANSPOSE)transA, m, n, alpha, A,
		lda, x, incx, beta, y, incy);
}

void BLASIntelMKL::dgemm(int order, int transA, int transB, int m, int n, int k,
						 double alpha, double A[], int lda, double B[], int ldb,
						 double beta, double C[], int ldc)
{
	cblas_dgemm((CBLAS_ORDER)order, (CBLAS_TRANSPOSE)transA, (CBLAS_TRANSPOSE)transB, 
		m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);	
}