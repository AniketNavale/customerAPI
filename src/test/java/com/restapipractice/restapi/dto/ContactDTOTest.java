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

public class ContactDTOTest {
	
	private static final Validator VALIDATOR =
		      Validation.buildDefaultValidatorFactory().getValidator();
	
	private ContactDTO contactDTO;
	
	@BeforeEach
	void setUp() {
		contactDTO = new ContactDTO();
	}

	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"' '", "'   '"})
	void testContactNumber_InvalidContactNumber(long contactNumber) {
		contactDTO.setContactNumber(contactNumber);
		testFieldInValid(contactDTO, "contactNumber", "No null contact number allowed");
	}
	
	@Test
	void testContactNumber_ValidContactNumber() {
		contactDTO.setContactNumber(4562585);
		testFieldValid(contactDTO, "contactNumber");
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
