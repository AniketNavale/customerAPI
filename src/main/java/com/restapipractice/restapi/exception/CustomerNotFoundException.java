package com.restapipractice.restapi.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(int customerId) {
		super("Customer not found");
    }
}
