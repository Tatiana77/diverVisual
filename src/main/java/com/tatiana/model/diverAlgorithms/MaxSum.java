package com.tatiana.model.diverAlgorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class MaxSum.
 */
public class MaxSum extends CalculationBase {

	/**
	 * K set cities.
	 *
	 * @param X the x
	 * @param k the k
	 * @return the city[]
	 */
	public City[] kSetCities(final City[] X, final int k) {
		City[] S = new City[k];
		int[] reference = new int[k];

		maxPair max = new maxPair();
		// Calculate similarity matrix of cities in X
		float[][] simMatrix = SimilarityMatrix.simMatrix(X, max);
		// SimilarityMatrix.printSimMatrixWithTitles(simMatrix, X);
		// System.out.println(max.getDist());

		// Set the first city from the max pair
		int x = max.getFirst();
		S[0] = X[x];
		reference[0] = x;
		if (k == 1) {
			return S;
		}

		// Calculate the set distance from the first city
		Map<Integer, Float> setDistance = new HashMap<Integer, Float>();

		for (int i = 0; i < X.length; i++) {
			setDistance.put(i, simMatrix[x][i]);
		}

		// Calculate the second city to add to S
		int maximum = max.getSecond();// max(setDistance, reference);
		S[1] = X[maximum];
		reference[1] = maximum;
		if (k == 2) {
			return S;
		}

		for (int j = 2; j < k; j++) {
			for (int i = 0; i < X.length; i++) {
				setDistance.put(i,
						(setDistance.get(i) + simMatrix[reference[j - 1]][i]));
			}
			maximum = max(setDistance, reference);
			S[j] = X[maximum];
			reference[j] = maximum;
		}

		return S;
	}


}
