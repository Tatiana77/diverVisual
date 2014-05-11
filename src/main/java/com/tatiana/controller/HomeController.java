package com.tatiana.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
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
import com.tatiana.web.Status;

@Controller
@SessionAttributes("formBean")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String ERROR = "ERROR";

	private static final String OK = "OK";

	private static final String ID = "id";

	private final AtomicInteger processCounter = new AtomicInteger();

	private final ConcurrentHashMap<Integer, Status> statusMap = new ConcurrentHashMap<Integer, Status>();

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

	@RequestMapping(value = "/ajax/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status getStatus(final HttpSession session) {
		Status status = checkStatus(session);
		return status;
	}

	private Status checkStatus(final HttpSession session) {
		Status status = null;
		Integer id = (Integer) session.getAttribute(ID);
		if (id != null) {
			status = statusMap.get(id);
		}
		if (id == null || status == null) {
			id = processCounter.incrementAndGet();
			session.setAttribute(ID, id);
			status = new Status("Processing");
			status.setId(id);
			logger.debug("Storing status id: " + id);
			statusMap.put(id, status);
		} else {
			status = statusMap.get(id);
		}
		return status;
	}

	private void clearStatus(final HttpSession session) {
		Integer id = (Integer) session.getAttribute(ID);
		if (id != null) {
			logger.debug("Cleanning status id: " + id);
			statusMap.remove(id);
			session.removeAttribute(ID);
		}
	}

	@RequestMapping(value = "/ajax/process", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	FormResponse process(@Valid @RequestBody final DiverVisualMBean formBean, final BindingResult result,
			final Model model, final HttpSession session) {
		Status status = checkStatus(session);
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
		try {
			processCalculation(formBean, response, status);
		} catch (Exception exception) {
			response.setStatus("ERROR");
			response.addError(null, "Calculation error!");
			response.addError(null, exception.toString());
			logger.error("Calculation error", exception);
		}
		clearStatus(session);
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
	private void processCalculation(final DiverVisualMBean formBean, final FormResponse response, final Status status) {
		City[] allCities = null;
		City[] diversifiedCities = null;
		// Check the calculation type
		AlgorithmType type = AlgorithmType.fromValue(formBean.getAlgorithm());
		if (type != null && status != null) {
			status.setMessage("Loading data...");
			allCities = getData(formBean.getInputType(), formBean);
			Assert.notNull(allCities, "A input type must be set");
			int numberOfCities = allCities.length;
			logger.debug(numberOfCities + " cities found");
			// If found cities, calculate
			if (numberOfCities > 1) {
				int k = Math.round(numberOfCities * formBean.getPercentage() / 100);
				switch (type) {
				case MAX_SUM:
					status.setMessage("Calculating sum max");
					diversifiedCities = calculationService.calculateMaxSum(allCities, k, status.getId(), statusMap);
					break;
				case MIN_DIV:
					status.setMessage("Calculating min div");
					diversifiedCities = calculationService.calculateMinDiv(allCities, k, status.getId(), statusMap);
					break;
				default:
					break;
				}
			} else {
				// If not, return empty array
				diversifiedCities = new City[0];
				response.setStatus("ERROR");
				response.addError(null, "Must have more than 1 city to calculate");
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
