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

	private City[] cities;

	public List<Error> getErrors() {
		return errors;
	}

	private String message;

	private final List<Error> errors = new ArrayList<>();

	public City[] getCities() {
		return cities;
	}

	public void setCities(final City[] cities) {
		this.cities = cities;
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

	public void addError(final String field , final String message){
		errors.add(new Error(field, message));
	}

}
