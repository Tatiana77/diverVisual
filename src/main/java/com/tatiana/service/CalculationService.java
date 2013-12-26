package com.tatiana.service;

import com.tatiana.model.diverAlgorithms.City;

public interface CalculationService {

	/**
	 * Calculate max sum.
	 *
	 * @param input the input
	 * @param k the k
	 * @return the city[]
	 */
	City[] calculateMaxSum(City[] input, int k);

	/**
	 * Calculate min div.
	 *
	 * @param input the input
	 * @param k the k
	 * @return the city[]
	 */
	City[] calculateMinDiv(City[] input, int k);
}
