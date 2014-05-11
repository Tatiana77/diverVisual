package com.tatiana.service;

import java.util.List;

import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.web.Country;

public interface DataService {

	/**
	 * Generate cities.
	 *
	 * @param country
	 *            the country
	 * @param population
	 *            the population
	 * @return the city[]
	 */
	City[] generateCities(final String country, final int population);

	/**
	 * Generate cities.
	 *
	 * @param nELat
	 *            the n e lat
	 * @param nELng
	 *            the n e lng
	 * @param sWLat
	 *            the s w lat
	 * @param sWLng
	 *            the s w lng
	 * @param population
	 *            the population
	 * @return the city[]
	 */
	City[] generateCities(final float nELat, final float nELng, final float sWLat, final float sWLng,
			final int population);

	/**
	 * Gets the country names.
	 *
	 * @return the country names
	 */
	List<Country> getCountries();

}
