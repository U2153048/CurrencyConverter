package com.currencyconverter.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.currencyconverter.apidemo", "com.currencyconverter.service" })
public class CurrencyConverterExerciseFormedixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterExerciseFormedixApplication.class, args);
		System.out.println("Welcome to Currency Converter.....");
	}

}
