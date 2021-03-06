/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package nativeInterface;

public class swigDoubleArray {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected swigDoubleArray(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(swigDoubleArray obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        BLASCppModuleJNI.delete_swigDoubleArray(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public swigDoubleArray(int nelements) {
    this(BLASCppModuleJNI.new_swigDoubleArray(nelements), true);
  }

  public double getitem(int index) {
    return BLASCppModuleJNI.swigDoubleArray_getitem(swigCPtr, this, index);
  }

  public void setitem(int index, double value) {
    BLASCppModuleJNI.swigDoubleArray_setitem(swigCPtr, this, index, value);
  }

  public SWIGTYPE_p_double cast() {
    long cPtr = BLASCppModuleJNI.swigDoubleArray_cast(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static swigDoubleArray frompointer(SWIGTYPE_p_double t) {
    long cPtr = BLASCppModuleJNI.swigDoubleArray_frompointer(SWIGTYPE_p_double.getCPtr(t));
    return (cPtr == 0) ? null : new swigDoubleArray(cPtr, false);
  }

}
