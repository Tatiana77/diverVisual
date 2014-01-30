package com.tatiana.model.diverAlgorithms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tatiana.web.Status;

/**
 * The Class CalculationBase.
 */
public abstract class CalculationBase {

	private static final Logger logger = LoggerFactory.getLogger(CalculationBase.class);

	private final Integer id;
	private final ConcurrentHashMap<Integer, Status> statusMap;

	public CalculationBase(final Integer id, final ConcurrentHashMap<Integer, Status> statusMap) {
		super();
		this.id = id;
		this.statusMap = statusMap;
	}

	protected void setMessage(final String message) {
		Status status = statusMap.get(id);
		if (status != null) {
			logger.debug("Updated message: " + message + " Id: " + id);
			status.setMessage(message);
		}
	}

	protected void setProgress(final int actual, final int max) {
		Status status = statusMap.get(id);
		if (status != null) {
			double actuald = actual * 1.0;
			Double progress = (actuald / max) * 100;
			logger.debug("Updated progress: " + progress + " Id: " + id);
			status.setProgress(progress.intValue());
		}
	}

	/**
	 * Max.
	 * 
	 * @param setDistance
	 *            the set distance
	 * @param S
	 *            the s
	 * @return the int
	 */
	protected int max(final Map<Integer, Float> setDistance, final int[] S) {
		float max = 0;
		int key = 0;
		for (int i = 0; i < setDistance.size(); i++) {
			boolean isInS = false;
			for (int j = 0; j < S.length; j++) {
				if (S[j] == i) {
					isInS = true;
				}
			}
			if (!isInS && (setDistance.get(i) > max)) {
				max = setDistance.get(i);
				key = i;
			}
		}
		return key;
	}

}