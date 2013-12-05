package blas;

import nativeInterface.swigDoubleArray;

public class RealArray {

	private double[] data;
	private final static double EPSILON = 0.0000001;

	public RealArray(double[] array) {
		this.data = array;
	}
	
	public RealArray(swigDoubleArray swigarray, int length) {
		this.data = new double[length];
		for (int i = 0; i < length; i++) {
			this.data[i] = swigarray.getitem(i);
		}
	}
	
	public void init(double value) {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = value;
		}
	}
	
	public swigDoubleArray toSwigDoubleArray() {
		swigDoubleArray swigarray = new swigDoubleArray(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			swigarray.setitem(i, this.data[i]);
		}
		return swigarray;
	}
	
	public boolean equals(RealArray other) {
		if (this.data.length != other.getLength()) {
			return false;
		}
		for (int i = 0; i < this.data.length; i++) {
			if (Math.abs(this.data[i] - other.get(i)) > RealArray.EPSILON) {
				return false;
			}
		}
		return true;
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
