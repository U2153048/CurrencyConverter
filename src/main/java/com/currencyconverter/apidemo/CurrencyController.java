package com.currencyconverter.apidemo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.currencyconverter.service.CurrencyService;

/**
 * @author Julit Francis This class acts as controller class
 */
@RestController
public class CurrencyController {
	@Autowired
	public CurrencyService currencyService;

	/**
	 * Method to load currencies from .csv to memory
	 * 
	 * @return csvData
	 */
	@RequestMapping(value = "/loadcurrency", method = RequestMethod.GET)
	public Map<String, HashMap<String, String>> loadCSV() {
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		System.out.println("Output.............." + csvData);
		csvData = currencyService.loadCurrencies();
		return csvData;

	}

	/**
	 * Method to load currency rate for date
	 * 
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/loadcurrencyratefordate", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, String> getCurrencyRateforDate(@RequestParam("date") String date) {
		System.out.println("Reached controller" + date);
		HashMap<String, String> currencyRateperDayMap = new HashMap<String, String>();
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		System.out.println("Output.............." + csvData);
		csvData = currencyService.loadCurrencies();
		currencyRateperDayMap = currencyService.getCurrencyRatePerDate(date, csvData);
		return currencyRateperDayMap;
	}

	/**
	 * Method to retrieve converted currency date
	 * 
	 * @param date
	 * @param sourcecurrency
	 * @param targetcurrency
	 * @param amount
	 * @return convertedCurrencyRate
	 */
	@RequestMapping(value = "/getconvertedcurrencyrate", method = RequestMethod.GET)
	public @ResponseBody double getConvertedCurrencyRate(@RequestParam("date") String date,
			@RequestParam("sourcecurrency") String sourcecurrency,
			@RequestParam("targetcurrency") String targetcurrency, @RequestParam("amount") long amount) {
		System.out.println("Reached controller" + date);
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		System.out.println("Output.............." + csvData);
		csvData = currencyService.loadCurrencies();
		double convertedCurrencyRate = currencyService.getConvertedCurrencyRate(date, sourcecurrency, targetcurrency,
				amount, csvData);
		return convertedCurrencyRate;
	}

	/**
	 * Retrieve highest currency reference rate from a given time period
	 * 
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return highestCurrencyRate
	 */
	@RequestMapping(value = "/gethighestrate", method = RequestMethod.GET)
	public @ResponseBody double getHighestRate(@RequestParam("startdate") String startdate,
			@RequestParam("enddate") String enddate, @RequestParam("currency") String currency) {
		System.out.println("Reached controller" + startdate);
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		System.out.println("Output.............." + csvData);
		csvData = currencyService.loadCurrencies();
		double highestCurrencyRate = currencyService.getHighestCurrencyRate(csvData, startdate, enddate, currency);
		return highestCurrencyRate;
	}

	/**
	 * Fetch average reference rate from a give time period
	 * 
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return avgCurrencyRate
	 */
	@RequestMapping(value = "/getaveragereferencerate", method = RequestMethod.GET)
	public @ResponseBody double getAvgReferenceRate(@RequestParam("startdate") String startdate,
			@RequestParam("enddate") String enddate, @RequestParam("currency") String currency) {
		System.out.println("Reached controller" + startdate);
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		System.out.println("Output.............." + csvData);
		csvData = currencyService.loadCurrencies();
		double avgCurrencyRate = currencyService.getAvgCurrencyRate(csvData, startdate, enddate, currency);
		return avgCurrencyRate;
	}
}
