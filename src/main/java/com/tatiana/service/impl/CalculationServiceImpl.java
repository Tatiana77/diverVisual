package com.tatiana.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.model.diverAlgorithms.MaxSum;
import com.tatiana.model.diverAlgorithms.MinDiv;
import com.tatiana.service.CalculationService;

@Service
public class CalculationServiceImpl implements CalculationService {

	private static final Logger logger = LoggerFactory.getLogger(CalculationServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.tatiana.service.CalculationService#calculateMaxSum(com.tatiana.model.diverAlgorithms.City[], int)
	 */
	@Override
	public City[] calculateMaxSum(final City[] input, final int k) {
		logger.debug("Calculating by: Max Sum Points: " + input.length);
		MaxSum maxSum = new MaxSum();
		return maxSum.kSetCities(input, k);
	}

	/* (non-Javadoc)
	 * @see com.tatiana.service.CalculationService#calculateMinDiv(com.tatiana.model.diverAlgorithms.City[], int)
	 */
	@Override
	public City[] calculateMinDiv(final City[] input, final int k) {
		logger.debug("Calculating by: Min Div Points: " + input.length);
		MinDiv minDiv = new MinDiv();
		return minDiv.kSetCities(input, k);
	}

}
