package com.tatiana.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tatiana.model.diverAlgorithms.InputType;

public class DiverVisualMBeanValidator implements Validator {

	private static final Logger logger = LoggerFactory.getLogger(DiverVisualMBeanValidator.class);

	@Override
	public boolean supports(final Class<?> clazz) {
		return DiverVisualMBean.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		DiverVisualMBean bean = (DiverVisualMBean) target;
		InputType type = InputType.fromValue(bean.getInputType());
		if (type != null) {
			switch (type) {
			case AREA:
				validateMapValues(bean, errors);
			case COUNTRY:
				validateCountryValues(bean, errors);
			default:
				break;
			}
		}
	}

	private void validateCountryValues(final DiverVisualMBean bean, final Errors errors) {
		logger.debug("Validating values: country");
		if (bean.getPercentage() < 1.00 || bean.getPercentage() > 100.00) {
			errors.rejectValue("percentage", "outOfRange", "Percentage value must be between 1 and 100");
		}


		if (StringUtils.isEmpty(bean.getCountry())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "country.empty","A country name must be selected");
		}
	}

	private void validateMapValues(final DiverVisualMBean bean, final Errors errors) {
		logger.debug("Validating values: map");

	}

}
