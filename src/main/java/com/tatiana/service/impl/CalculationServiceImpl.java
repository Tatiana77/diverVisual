package com.tatiana.service.impl;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.model.diverAlgorithms.MaxSum;
import com.tatiana.model.diverAlgorithms.MaxMin;
import com.tatiana.service.CalculationService;
import com.tatiana.web.Status;

@Service
public class CalculationServiceImpl implements CalculationService {

	private static final Logger logger = LoggerFactory.getLogger(CalculationServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tatiana.service.CalculationService#calculateMaxSum(com.tatiana.model
	 * .diverAlgorithms.City[], int)
	 */

	@Override
	public City[] calculateMaxSum(final City[] input, final int k, final Integer id,
			final ConcurrentHashMap<Integer, Status> statusMap) {
		logger.debug("Calculating by: Max Sum Points: " + input.length);
		MaxSum maxSum = new MaxSum(id, statusMap);
		City[] dcities = maxSum.kSetCities(input, k);
		System.gc();
		return Arrays.copyOf(dcities, dcities.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tatiana.service.CalculationService#calculateMinDiv(com.tatiana.model
	 * .diverAlgorithms.City[], int)
	 */
	@Override
	public City[] calculateMinDiv(final City[] input, final int k, final Integer id,
			final ConcurrentHashMap<Integer, Status> statusMap) {
		logger.debug("Calculating by: Min Div Points: " + input.length);
		MaxMin minDiv = new MaxMin(id, statusMap);
		City[] dcities = minDiv.kSetCities(input, k);
		minDiv = null;
		System.gc();
		return Arrays.copyOf(dcities, dcities.length);
	}

}
