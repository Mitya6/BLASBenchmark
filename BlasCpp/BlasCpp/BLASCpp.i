/* File: BLASCpp.i */
%module(directors=1) BLASCppModule
%{
#include "BLASNagC.h"
#include "BLASIntelMKL.h"
%}

%include "carrays.i"
%array_functions(double, doubleArray);

%include "BLASNagC.h"
%include "BLASIntelMKL.h"