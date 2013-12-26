package com.tatiana.model.diverAlgorithms;

import java.util.Map;

/**
 * The Class CalculationBase.
 */
public abstract class CalculationBase {

	/**
	 * Instantiates a new calculation base.
	 */
	public CalculationBase() {
		super();
	}

	/**
	 * Max.
	 *
	 * @param setDistance the set distance
	 * @param S the s
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