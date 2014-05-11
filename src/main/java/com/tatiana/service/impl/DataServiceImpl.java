package com.tatiana.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tatiana.data.DBConnect;
import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.service.DataService;
import com.tatiana.web.Country;

/**
 * The Class DataServiceImpl.
 */
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DBConnect dbConnect;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.service.DataService#generateCities(java.lang.String,
	 * int)
	 */
	@Override
	public City[] generateCities(final String country, final int population) {
		return dbConnect.generateCities(country, population);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.service.DataService#generateCities(float, float, float,
	 * float, int)
	 */
	@Override
	public City[] generateCities(final float nELat, final float nELng, final float sWLat, final float sWLng,
			final int population) {
		return dbConnect.generateCities(nELat, nELng, sWLat, sWLng, population);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tatiana.service.DataService#getCountries()
	 */
	@Override
	public List<Country> getCountries() {
		Map<String, String> countries = dbConnect.getCountryNames();
		List<Country> countriesList = new ArrayList<>();

		for (String key : countries.keySet()) {
			String code = countries.get(key);
			Country country = new Country(key, code);

			countriesList.add(country);
		}

		return countriesList;

	}

}
