package com.restapipractice.restapi.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class CustomerAddressTest {
	
	private static final Validator VALIDATOR =
		      Validation.buildDefaultValidatorFactory().getValidator();
	
	private Address customerAddress;
	
	@BeforeEach
	void setUp() {
		customerAddress = new Address();
	}

	@Test
	void testAddressCity_ValidCityName() {
		customerAddress.setCity("Mumbai");
		testFieldValid(customerAddress, "city");
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testAddressCity_InValidCityName(String city) {
		customerAddress.setCity(city);
		testFieldInValid(customerAddress, "city", "No null city allowed");
	}
	
	@Test
	void testAddressCountry_ValidCountryName() {
		customerAddress.setCountry("India");
		testFieldValid(customerAddress, "country");
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testAddressCountry_InValidCountryName(String country) {
		customerAddress.setCountry(country);
		testFieldInValid(customerAddress, "country", "No null country allowed");
	}
	
	@Test
	void testAddressPostalCode_ValidPostalCode() {
		customerAddress.setPostal_code(400085);
		testFieldValid(customerAddress, "postal_code");
	}
	
	@ParameterizedTest
	@CsvSource({"-5","-452354"})
	void testAddressPostalCode_InvalidPostalCode_lessThanLimit(int postal_code) {
		customerAddress.setPostal_code(postal_code);
		testFieldInValid(customerAddress, "postal_code", "must be greater than or equal to 1");
	}
	
	@ParameterizedTest
	@CsvSource({"1058426","8556242"})
	void testAddressPostalCode_InvalidPostalCode_greaterThanLimit(int postal_code) {
		customerAddress.setPostal_code(postal_code);
		testFieldInValid(customerAddress, "postal_code", "must be less than or equal to 999999");
	}
	
	
	
	private static <T> void testFieldInValid(T object, String fieldName, String message) {
	    Set<ConstraintViolation<T>> violations = VALIDATOR.validateProperty(object, fieldName);
	    
	    assertThat(violations).hasSize(1);
	    assertThat(violations.iterator().next())
	        .extracting("interpolatedMessage")
	        .containsExactly(message);
	}
	
	private static <T> void testFieldValid(T object, String fieldName) {
	    assertThat(VALIDATOR.validateProperty(object, fieldName)).hasSize(0);
	}
}
