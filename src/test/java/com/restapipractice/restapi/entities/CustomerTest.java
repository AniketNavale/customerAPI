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

public class CustomerTest {

	private static final Validator VALIDATOR =
		      Validation.buildDefaultValidatorFactory().getValidator();
	
	private Customer customer;
	
	@BeforeEach
	void setUp() {
		customer = new Customer();
	}
	
	// valid customer
	// 1. only requesire field set
	// 2. all fields set
	
	// name validation
	// 1. invalid names
	
	// age validation
	// 1. invalid age
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testCustomerName_InvalidNames(String name) {
		customer.setName(name);
		testFieldInValid(customer, "name", "No null name allowed");
	}
	
	@Test
	void testCustomerName_ValidNames() {
		customer.setName("Aniket");
		testFieldValid(customer, "name");
	}
	
	@ParameterizedTest
	@CsvSource({"-5","2","4"})
	void testCustomerAge_InvalidAge_lessThanLimit(int age) {
		customer.setAge(age);
		testFieldInValid(customer, "age", "must be greater than or equal to 5");
	}
	
	@ParameterizedTest
	@CsvSource({"120","250"})
	void testCustomerAge_InvalidAge_greaterThanLimit(int age) {
		customer.setAge(age);
		testFieldInValid(customer, "age", "must be less than or equal to 110");
	}
	
	
	@Test
	void testCustomerAge_ValidAge() {
		customer.setAge(25);
		testFieldValid(customer, "age");
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
