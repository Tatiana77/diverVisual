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

	@RequestMapping(value = "/editCountries", method = RequestMethod.GET)
	public String editCountries() {
		return "editCountries";
	}

	@RequestMapping(value = "/ajax/process", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
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
		processCalculation(formBean, response);
		return response;

	}

	/**
	 * Process calculation according with the algorithm type.
	 * 
	 * @param formBean
	 *            the form bean
	 * @param response
	 * @return the city[]
	 */
	private void processCalculation(final DiverVisualMBean formBean, final FormResponse response) {
		City[] allCities = null;
		City[] diversifiedCities = null;
		// Check the calculation type
		AlgorithmType type = AlgorithmType.fromValue(formBean.getAlgorithm());
		if (type != null) {
			allCities = getData(formBean.getInputType(), formBean);
			Assert.notNull(allCities, "A input type must be set");
			int numberOfCities = allCities.length;

			// If found cities, calculate
			if (numberOfCities > 0) {
				int k = Math.round(numberOfCities * formBean.getPercentage() / 100);
				switch (type) {
				case MAX_SUM:
					diversifiedCities = calculationService.calculateMaxSum(allCities, k);
					break;
				case MIN_DIV:
					diversifiedCities = calculationService.calculateMinDiv(allCities, k);
					break;
				default:
					break;
				}
			} else {
				// If not, return empty array
				diversifiedCities = new City[0];
			}
		}
		response.setAllCities(allCities);
		response.setDiversifiedCities(diversifiedCities);
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
