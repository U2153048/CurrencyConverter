package com.currencyconverter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

/**
 * @author Julit Francis This class is for service implementation
 *
 */
@Component
public class CurrencyServiceImpl implements CurrencyService {

	/**
	 * Method to load currencies
	 * 
	 * @return
	 */
	public Map<String, HashMap<String, String>> loadCurrencies() {
		Map<String, HashMap<String, String>> csvData = new HashMap<String, HashMap<String, String>>();
		try {
			System.out.println("Service Implementaion ");
			ClassLoader classLoader = getClass().getClassLoader();
			 
	        File file = new File(classLoader.getResource("eurofxref-hist.csv").getFile());
			String fileName = "C:\\Users\\HP\\Desktop\\New folder\\formedix\\eurofxref-hist.csv";

			String row = "";
			BufferedReader csvReader = new BufferedReader(new FileReader(fileName));

			int firstLine = 0;
			String[] headingData = null;
			String[] rowData = null;

			while ((row = csvReader.readLine()) != null) {
				HashMap<String, String> csvRowDataMap = new HashMap<String, String>();
				if (firstLine == 0) {
					headingData = row.split(",");
					// do something with the data
					firstLine++;
					continue;
				} else {
					rowData = row.split(",");

				}
				for (int i = 1; i < headingData.length; i++) {

					csvRowDataMap.put(headingData[i], rowData[i]);
				}
				csvData.put(rowData[0], csvRowDataMap);
			}

			System.out.println(csvData);
			csvReader.close();

		} catch (IOException e) {

		}
		return csvData;

	}

	/**
	 * Method to get currency rate per date
	 * 
	 * @param date
	 * @param csvData
	 * @return currencyRatePerDateMap
	 */
	@Override
	public HashMap<String, String> getCurrencyRatePerDate(String date, Map<String, HashMap<String, String>> csvData) {
		HashMap<String, String> currencyRatePerDateMap = new HashMap<String, String>();
		try {
			if (csvData.containsKey(date)) {
				currencyRatePerDateMap = csvData.get(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currencyRatePerDateMap;

	}

	/**
	 * Method to get converted currency rate on a particular date
	 * 
	 * @param date
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @param amount
	 * @param csvData
	 * @return convertedRate
	 */
	@Override
	public double getConvertedCurrencyRate(String date, String sourceCurrency, String targetCurrency, long amount,
			Map<String, HashMap<String, String>> csvData) {
		HashMap<String, String> currencyRatePerDateMap = new HashMap<String, String>();
		String sourceCurrencyRate = "";
		String targetCurrencyRate = "";
		double convertedRate = 0;
		try {
			if (csvData.containsKey(date)) {
				currencyRatePerDateMap = csvData.get(date);
			}
			if (currencyRatePerDateMap.containsKey(sourceCurrency)) {
				sourceCurrencyRate = currencyRatePerDateMap.get(sourceCurrency);
			}
			if (currencyRatePerDateMap.containsKey(targetCurrency)) {
				targetCurrencyRate = currencyRatePerDateMap.get(targetCurrency);
			}
			double sourceCRate = Double.parseDouble(sourceCurrencyRate);
			double targetCRate = Double.parseDouble(targetCurrencyRate);
			if (sourceCRate < targetCRate) {
				convertedRate = amount * targetCRate;
			} else if (sourceCRate >= targetCRate) {
				convertedRate = amount / sourceCRate;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedRate;
	}

	/**
	 * method to get highest currency reference rate on a given time frame
	 * 
	 * @param csvData
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return highRate
	 */
	@Override
	public double getHighestCurrencyRate(Map<String, HashMap<String, String>> csvData, String startdate, String enddate,
			String currency) {
		double highRate = 0;

		// TODO Auto-generated method stub
		try {

			Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
			Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
			for (Entry<String, HashMap<String, String>> entry : csvData.entrySet()) {

				Date keyDate = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getKey());
				if (sDate.compareTo(keyDate) * keyDate.compareTo(eDate) >= 0) {
					System.out.println(keyDate + " is in between " + sDate + " and " + eDate);
					for (Entry<String, String> entry1 : entry.getValue().entrySet()) {

						if (entry1.getKey().equalsIgnoreCase(currency)) {
							double data = Double.parseDouble(entry1.getValue());
							if (data > highRate) {
								highRate = data;
							}
							break;
						}

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return highRate;
	}

	/**
	 * Method to get average currency reference date on a given time frame
	 * 
	 * @param csvData
	 * @param startdate
	 * @param enddate
	 * @param currency
	 * @return avg
	 */
	@Override
	public double getAvgCurrencyRate(Map<String, HashMap<String, String>> csvData, String startdate, String enddate,
			String currency) {
		double totalRate = 0;
		int count = 0;
		double avg = 0;

		// TODO Auto-generated method stub
		try {

			Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
			Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
			for (Entry<String, HashMap<String, String>> entry : csvData.entrySet()) {

				Date keyDate = new SimpleDateFormat("yyyy-MM-dd").parse(entry.getKey());
				if (sDate.compareTo(keyDate) * keyDate.compareTo(eDate) >= 0) {
					System.out.println("in between");

					for (Entry<String, String> entry1 : entry.getValue().entrySet()) {

						if (entry1.getKey().equalsIgnoreCase(currency)) {
							double data = Double.parseDouble(entry1.getValue());
							totalRate = totalRate + data;
							count = count + 1;
							break;
						}

					}

				} else {

				}
			}
			avg = totalRate / count;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return avg;
	}
}
