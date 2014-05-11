package com.tatiana.model.diverAlgorithms;

public class maxPair {
	float dist;
	int a;
	int b;

	public maxPair() {
		this.dist = 0;
	}

	public double getDist() {
		return this.dist;
	}

	public int getFirst() {
		return this.a;
	}

	public int getSecond() {
		return this.b;
	}

	public void setDist(final float dist) {
		this.dist = dist;
	}

	public void setA(final int a) {
		this.a = a;
	}

	public void setB(final int b) {
		this.b = b;
	}
}
