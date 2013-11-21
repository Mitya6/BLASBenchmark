package blas;

import nativeInterface.BLASCppModule;
import nativeInterface.SWIGTYPE_p_double;

public class RealArray {

	private double[] data;

	public RealArray(double[] array) {
		this.data = array;
	}

	public RealArray(SWIGTYPE_p_double swigarray, int length) {
		this.data = new double[length];
		for (int i = 0; i < length; i++) {
			this.data[i] = BLASCppModule.doubleArray_getitem(swigarray, i);
		}
	}

	public double[] toDouble() {
		return this.data;
	}

	public SWIGTYPE_p_double toSWIGTYPE_p_double() {
		SWIGTYPE_p_double swigarray = BLASCppModule
				.new_doubleArray(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			BLASCppModule.doubleArray_setitem(swigarray, i, this.data[i]);
		}
		return swigarray;
	}

	public int getLength() {
		return this.data.length;
	}

	public double get(int idx) {
		return this.data[idx];
	}

	public void set(int idx, double value) {
		this.data[idx] = value;
	}

	public void setData(double[] value) {
		this.data = value;
	}
	
	public double[] getData() {
		return this.data;
	}
}
