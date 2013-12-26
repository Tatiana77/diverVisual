package com.tatiana.model.diverAlgorithms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public enum InputType {

	AREA("map"),

	COUNTRY("country");

	private final String value;

	private static final Map<String, InputType> valueToEnum = new HashMap<String, InputType>();

	static {
		for (InputType type : values()) {
			valueToEnum.put(type.value, type);
		}
	}

	InputType(final String value) {
		this.value = value;
	}

	public static InputType fromValue(final String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return valueToEnum.get(value);
	}
}
