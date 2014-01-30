package com.tatiana.service;

import java.util.concurrent.ConcurrentHashMap;

import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.web.Status;

public interface CalculationService {

	/**
	 * Calculate max sum.
	 * 
	 * @param input
	 *            : The set of all cities;
	 * @param k
	 *            : number of diversified cities to be calculated.
	 * @return the city[]
	 */
	City[] calculateMaxSum(City[] input, int k, Integer id, ConcurrentHashMap<Integer, Status> statusMap);

	/**
	 * Calculate min div.
	 * 
	 * @param input
	 *            : The set of all cities;
	 * @param k
	 *            : number of diversified cities to be calculated.
	 * @return the city[]
	 */
	City[] calculateMinDiv(City[] input, int k, Integer id, ConcurrentHashMap<Integer, Status> statusMap);
}
