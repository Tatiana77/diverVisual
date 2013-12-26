package com.tatiana.model.diverAlgorithms;

public class SimilarityMatrix {
	public static float[][] simMatrix(final City[] citiesArray) {
		float simMatrix[][] = new float[citiesArray.length][citiesArray.length];
		for (int i = 0; i < citiesArray.length; i++) {
			for (int j = i; j < citiesArray.length; j++) {
				simMatrix[i][j] = EuclideanDistance.euclideanDistance(
						citiesArray[i], citiesArray[j]);
				simMatrix[j][i] = simMatrix[i][j];
			}
		}
		return simMatrix;
	}

	public static float[][] simMatrix(final City[] citiesArray,
			final maxPair max) {
		// System.out.println(max.getDist());
		float simMatrix[][] = new float[citiesArray.length][citiesArray.length];
		for (int i = 0; i < citiesArray.length; i++) {
			for (int j = i; j < citiesArray.length; j++) {
				simMatrix[i][j] = EuclideanDistance.euclideanDistance(
						citiesArray[i], citiesArray[j]);
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
