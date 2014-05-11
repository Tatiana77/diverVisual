package com.tatiana.model.diverAlgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tatiana.web.Status;

/**
 * The Class MaxSum.
 */
public class MaxSum extends CalculationBase {

	/**
	 * K set cities.
	 * 
	 * @param X
	 *            The set of all the cities
	 * @param k
	 *            the number of diversified cities to be retrieved
	 * @return the set of k diversified cities in an array
	 */

	public MaxSum(final Integer id, final ConcurrentHashMap<Integer, Status> statusMap) {
		super(id, statusMap);
	}

	public City[] kSetCities(final City[] X, final int k) {
		City[] S = new City[k];
		int[] reference = new int[k];

		maxPair max = new maxPair();
		// Calculate similarity matrix of cities in X
		setMessage("Calculating similarity matrix...");
		SimilarityMatrix matrix = new SimilarityMatrix(this);
		float[][] simMatrix = matrix.simMatrix(X, max);
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
			setMessage("Setting distances...");
			setDistance.put(i, simMatrix[x][i]);
		}

		// Calculate the second city to add to S
		int maximum = max.getSecond();// max(setDistance, reference);
		S[1] = X[maximum];
		reference[1] = maximum;
		if (k == 2) {
			return S;
		}

		setMessage("Calculating...");
		for (int j = 2; j < k; j++) {
			setProgress(j, k);
			for (int i = 0; i < X.length; i++) {
				setDistance.put(i, (setDistance.get(i) + simMatrix[reference[j - 1]][i]));
			}
			maximum = max(setDistance, reference);
			S[j] = X[maximum];
			reference[j] = maximum;
		}

		return S;
	}

}
