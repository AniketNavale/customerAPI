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

import com.restapipractice.restapi.dto.CustomerDTO;

public class CustomerDTOTest {

	private static final Validator VALIDATOR =
		      Validation.buildDefaultValidatorFactory().getValidator();
	
	private CustomerDTO customerDTO;
	
	@BeforeEach
	void setUp() {
		customerDTO = new CustomerDTO();
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testCustomerName_InvalidNames(String name) {
		customerDTO.setName(name);
		testFieldInValid(customerDTO, "name", "No null name allowed");
	}
	
	@Test
	void testCustomerName_ValidNames() {
		customerDTO.setName("Aniket");
		testFieldValid(customerDTO, "name");
	}
	
	@ParameterizedTest
	@CsvSource({"-5","2","4"})
	void testCustomerAge_InvalidAge_lessThanLimit(int age) {
		customerDTO.setAge(age);
		testFieldInValid(customerDTO, "age", "must be greater than or equal to 5");
	}
	
	@ParameterizedTest
	@CsvSource({"120","250"})
	void testCustomerAge_InvalidAge_greaterThanLimit(int age) {
		customerDTO.setAge(age);
		testFieldInValid(customerDTO, "age", "must be less than or equal to 110");
	}
	
	
	@Test
	void testCustomerAge_ValidAge() {
		customerDTO.setAge(25);
		testFieldValid(customerDTO, "age");
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
