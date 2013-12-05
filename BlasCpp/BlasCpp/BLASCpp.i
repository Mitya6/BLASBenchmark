/* File: BLASCpp.i */
%module(directors=1) BLASCppModule
%{
#include "BLASNagC.h"
#include "BLASIntelMKL.h"
%}

%include "carrays.i"
%array_class(double, swigDoubleArray);
%array_class(int, swigIntArray);

%include "BLASNagC.h"
%include "BLASIntelMKL.h"