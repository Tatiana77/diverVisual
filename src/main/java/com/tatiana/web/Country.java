package com.tatiana.web;

public class Country {

	private final String name;

	private final String code;

	public Country(final String name, final String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", code=" + code + "]";
	}

}
