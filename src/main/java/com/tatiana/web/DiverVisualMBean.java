package com.tatiana.web;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.tatiana.model.diverAlgorithms.AlgorithmType;

public class DiverVisualMBean {

	private String country;

	private int population;

	@Max(value = 100)
	@Min(value = 1)
	private float percentage = 1;

	private String algorithm = "minDiv";

	private float nELat = 0;

	private float nELng = 0;

	private float sWLat = 0;

	private Country currentCountry;

	public Country getCurrentCountry() {
		return currentCountry;
	}

	public void setCurrentCountry(final Country currentCountry) {
		this.currentCountry = currentCountry;
	}

	public AlgorithmType[] getAlgorithmTypes() {
		return AlgorithmType.values();
	}

	private float sWLng = 0;

	private String inputType = "country";

	private List<Country> countries;

	public DiverVisualMBean() {

	}

	public DiverVisualMBean(final List<Country> countries) {
		super();
		this.countries = countries;
	}

	public float getnELat() {
		return nELat;
	}

	public void setnELat(final float nELat) {
		this.nELat = nELat;
	}

	public float getnELng() {
		return nELng;
	}

	public void setnELng(final float nELng) {
		this.nELng = nELng;
	}

	public float getsWLat() {
		return sWLat;
	}

	public void setsWLat(final float sWLat) {
		this.sWLat = sWLat;
	}

	public float getsWLng() {
		return sWLng;
	}

	public void setsWLng(final float sWLng) {
		this.sWLng = sWLng;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(final String inputType) {
		this.inputType = inputType;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(final float percentage) {
		this.percentage = percentage;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(final int population) {
		this.population = population;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public Collection<Country> getCountries() {
		return countries;
	}

	@Override
	public String toString() {
		return "DiverVisualMBean [country=" + country + ", population=" + population + ", percentage=" + percentage
				+ ", algorithm=" + algorithm + ", nELat=" + nELat + ", nELng=" + nELng + ", sWLat=" + sWLat
				+ ", sWLng=" + sWLng + ", inputType=" + inputType + "]";
	}

}
