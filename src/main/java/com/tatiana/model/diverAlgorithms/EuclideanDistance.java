package com.tatiana.model.diverAlgorithms;

public class EuclideanDistance {
	public static float euclideanDistance(final City a, final City b) {
		float vx, vy;
		vx = Math.abs(a.getLatitude().floatValue()
				- b.getLatitude().floatValue());
		vy = Math.abs(a.getLongitude().floatValue()
				- b.getLongitude().floatValue());

		// System.out.println("Distance between: " + a.toString() + " and " +
		// b.toString()
		// + " = " + Math.sqrt(vx + vy));

		return (float) Math.sqrt(vx + vy);
	}
}
