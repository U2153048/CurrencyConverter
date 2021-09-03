package com.currencyconverter.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julit Francis This is a service interface for currency converter
 *
 */
public interface CurrencyService {
	/**
	 * Method to load currencies
	 * 
	 * @return
	 */
	public Map<String, HashMap<String, String>> loadCurrencies();

	/**
	 * Method to get currency rate per date
	 * 
	 * @param date
	 * @param csvData
	 * @return
	 */
	public HashMap<String, String> getCurrencyRatePerDate(String date, Map<String, HashMap<String, String>> csvData);

	/**
	 * Method to get converted currency rate on a particular date
	 * 
	 * @param date
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @param amount
	 * @param csvData
	 * @return
	 */
	public double getConvertedCurrencyRate(String date, String sourceCurrency, String targetCurrency, long amount,
			Map<String, HashMap<String, String>> csvData);

	/**
	 * method to get highest currency reference rate on a given time frame
	 * 
	 * @param csvData
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return
	 */
	public double getHighestCurrencyRate(Map<String, HashMap<String, String>> csvData, String startdate, String enddate,
			String currency);

	/**
	 * Method to get average currency reference date on a given time frame
	 * 
	 * @param csvData
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return
	 */
	public double getAvgCurrencyRate(Map<String, HashMap<String, String>> csvData, String startdate, String enddate,
			String currency);

}
