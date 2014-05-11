package com.tatiana.data;

import java.util.Map;

import com.tatiana.model.diverAlgorithms.City;

public interface DBConnect {

	public abstract Map<String, String> getCountryNames();

	public abstract City[] generateCities(String country, int population);

	public abstract City[] generateCities(float nELat, float nELng,
			float sWLat, float sWLng, int population);

}