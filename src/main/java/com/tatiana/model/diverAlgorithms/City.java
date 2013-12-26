package com.tatiana.model.diverAlgorithms;

import java.math.BigDecimal;

public class City {
	private final String country;
	private final String city_ascii;
	private final String city;
	private final String region;
	private final Long population;
	private final BigDecimal latitude;
	private final BigDecimal longitude;

	public City(final String country, final String city_ascii,
			final String city, final String region, final Long population,
			final BigDecimal latitude, final BigDecimal longitude) {
		this.country = country;
		this.city_ascii = city_ascii;
		this.city = city;
		this.region = region;
		this.population = population;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCity() {
		return this.city;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	@Override
	public String toString() {
		return this.city_ascii + "," + this.latitude + "," + this.longitude;
	}

}
