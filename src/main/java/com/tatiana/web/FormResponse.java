package com.tatiana.web;

import java.util.ArrayList;
import java.util.List;

import com.tatiana.model.diverAlgorithms.City;

public class FormResponse {

	public FormResponse() {
		super();
	}

	public FormResponse(final String status) {
		super();
		this.status = status;
	}

	public FormResponse(final String status, final String message) {
		super();
		this.status = status;
		this.message = message;
	}

	private String status;

	private City[] allCities;
	private City[] diversifiedCities;

	public List<Error> getErrors() {
		return errors;
	}

	private String message;

	private final List<Error> errors = new ArrayList<>();

	public City[] getAllCities() {
		return allCities;
	}

	public void setAllCities(final City[] allCities) {
		this.allCities = allCities;
	}

	public City[] getDiversifiedCities() {
		return diversifiedCities;
	}

	public void setDiversifiedCities(final City[] diversifiedCities) {
		this.diversifiedCities = diversifiedCities;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void addError(final String field, final String message) {
		errors.add(new Error(field, message));
	}

}
