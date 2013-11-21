#include "BLASNagC.h"
#include <chrono>
#include <thread>

using namespace BLAS;
using namespace std;

//void BLASNagC::daxpy(int n, double alpha, double x[], int incx,
//					 double y[], int incy)
//{
//
//}

void BLASNagC::daxpby(int n, double alpha, double x[], int incx,
					  double beta, double y[], int incy)
{
	this_thread::sleep_for(chrono::milliseconds(6500));

	// return test data
	for (int i = 0; i < n; i++)
	{
		y[i] = -2.0;
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