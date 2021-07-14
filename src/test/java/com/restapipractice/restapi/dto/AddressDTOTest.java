package com.restapipractice.restapi.dto;

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

import com.restapipractice.restapi.entities.Address;

public class AddressDTOTest {
	
	private static final Validator VALIDATOR =
		      Validation.buildDefaultValidatorFactory().getValidator();
	
	private Address address;
	
	@BeforeEach
	void setUp() {
		address = new Address();
	}

	@Test
	void testAddressCity_ValidCityName() {
		address.setCity("Mumbai");
		testFieldValid(address, "city");
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testAddressCity_InValidCityName(String city) {
		address.setCity(city);
		testFieldInValid(address, "city", "No null city allowed");
	}
	
	@Test
	void testAddressCountry_ValidCountryName() {
		address.setCountry("India");
		testFieldValid(address, "country");
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testAddressCountry_InValidCountryName(String country) {
		address.setCountry(country);
		testFieldInValid(address, "country", "No null country allowed");
	}
	
	@Test
	void testAddressPostalCode_ValidPostalCode() {
		address.setPostal_code(400085);
		testFieldValid(address, "postal_code");
	}
	
	@ParameterizedTest
	@CsvSource({"-5","-452354"})
	void testAddressPostalCode_InvalidPostalCode_lessThanLimit(int postal_code) {
		address.setPostal_code(postal_code);
		testFieldInValid(address, "postal_code", "must be greater than or equal to 1");
	}
	
	@ParameterizedTest
	@CsvSource({"1058426","8556242"})
	void testAddressPostalCode_InvalidPostalCode_greaterThanLimit(int postal_code) {
		address.setPostal_code(postal_code);
		testFieldInValid(address, "postal_code", "must be less than or equal to 999999");
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
