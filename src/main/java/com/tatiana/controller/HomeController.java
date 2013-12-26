package com.tatiana.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tatiana.model.diverAlgorithms.AlgorithmType;
import com.tatiana.model.diverAlgorithms.City;
import com.tatiana.model.diverAlgorithms.InputType;
import com.tatiana.service.CalculationService;
import com.tatiana.service.DataService;
import com.tatiana.web.Country;
import com.tatiana.web.DiverVisualMBean;
import com.tatiana.web.DiverVisualMBeanValidator;
import com.tatiana.web.FormResponse;

@Controller
@SessionAttributes("formBean")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String ERROR = "ERROR";

	private static final String OK = "OK";

	@Autowired
	private DataService dataService;

	@Autowired
	private CalculationService calculationService;

	private List<Country> countriesList;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public void form() {

	}

	@RequestMapping(value = "/ajax/process", method = RequestMethod.POST, produces = "application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FormResponse process(@Valid @RequestBody final DiverVisualMBean formBean, final BindingResult result,
			final Model model) {
		logger.debug("Processing: " + formBean);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		if (result.hasErrors()) {

			FormResponse errorResponse = new FormResponse(ERROR);
			List<FieldError> allErrors = result.getFieldErrors();
			for (FieldError error : allErrors) {
				errorResponse.addError(error.getField(), error.getDefaultMessage());
			}

			return errorResponse;
		}
		FormResponse response = new FormResponse(OK);
		response.setCities(processCalculation(formBean));
		return response;

	}

	/**
	 * Process calculation according with the algorithm type.
	 * 
	 * @param formBean
	 *            the form bean
	 * @return the city[]
	 */
	private City[] processCalculation(final DiverVisualMBean formBean) {
		City[] input = null;
		City[] output = null;
		// Check the calculation type
		AlgorithmType type = AlgorithmType.fromValue(formBean.getAlgorithm());
		if (type != null) {
			input = getData(formBean.getInputType(), formBean);
			Assert.notNull(input, "A input type must be set");
			int numberOfCities = input.length;

			// If found cities, calculate
			if (numberOfCities > 0) {
				int k = Math.round(numberOfCities * formBean.getPercentage() / 100);
				switch (type) {
				case MAX_SUM:
					output = calculationService.calculateMaxSum(input, k);
					break;
				case MIN_DIV:
					output = calculationService.calculateMinDiv(input, k);
					break;
				default:
					break;
				}
			} else {
				// If not, return empty array
				output = new City[0];
			}
		}
		return output;
	}

	private City[] getData(final String inputType, final DiverVisualMBean formBean) {
		InputType type = InputType.fromValue(inputType);
		if (type != null) {
			switch (type) {
			case AREA:
				return dataService.generateCities(formBean.getnELat(), formBean.getnELng(), formBean.getsWLat(),
						formBean.getsWLng(), formBean.getPopulation());
			case COUNTRY:
				return dataService.generateCities(formBean.getCountry(), formBean.getPopulation());
			default:
				break;
			}
		}
		return null;
	}

	@ModelAttribute("formBean")
	public DiverVisualMBean createFormBean() {
		if (countriesList == null) {
			countriesList = dataService.getCountries();
		}
		return new DiverVisualMBean(countriesList);

	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.setValidator(new DiverVisualMBeanValidator());
	}
}
