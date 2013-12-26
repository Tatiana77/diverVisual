package com.tatiana.model.diverAlgorithms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public enum AlgorithmType {

	MAX_SUM("Max Num"),

	MIN_DIV("Min Div");

	private final String name;

	private static final Map<String, AlgorithmType> valueToEnum = new HashMap<String, AlgorithmType>();

	static {
		for (AlgorithmType type : values()) {
			valueToEnum.put(type.toString(), type);
		}
	}

	public String getName() {
		return name;
	}

	AlgorithmType(final String name) {
		this.name = name;
	}

	public static AlgorithmType fromValue(final String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return valueToEnum.get(value);
	}
}
