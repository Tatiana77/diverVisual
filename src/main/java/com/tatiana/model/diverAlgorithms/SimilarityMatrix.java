package com.tatiana.model.diverAlgorithms;

public class SimilarityMatrix {

	private final CalculationBase base;

	public SimilarityMatrix(final CalculationBase base) {
		super();
		this.base = base;
	}

	public float[][] simMatrix(final City[] citiesArray) {
		float simMatrix[][] = new float[citiesArray.length][citiesArray.length];
		for (int i = 0; i < citiesArray.length; i++) {
			base.setProgress(i, citiesArray.length);
			for (int j = i; j < citiesArray.length; j++) {
				simMatrix[i][j] = EuclideanDistance.euclideanDistance(citiesArray[i], citiesArray[j]);
				simMatrix[j][i] = simMatrix[i][j];
			}
		}
		return simMatrix;
	}

	public float[][] simMatrix(final City[] citiesArray, final maxPair max) {
		// System.out.println(max.getDist());
		float simMatrix[][] = new float[citiesArray.length][citiesArray.length];
		for (int i = 0; i < citiesArray.length; i++) {
			base.setProgress(i, citiesArray.length);
			for (int j = i; j < citiesArray.length; j++) {
				simMatrix[i][j] = EuclideanDistance.euclideanDistance(citiesArray[i], citiesArray[j]);
				simMatrix[j][i] = simMatrix[i][j];
				if (simMatrix[i][j] > max.getDist()) {
					max.setDist(simMatrix[i][j]);
					max.setA(i);
					max.setB(j);
				}
			}
		}
		// System.out.println(max.getDist());
		return simMatrix;
	}
}
