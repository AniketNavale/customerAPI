package com.restapipractice.restapi.exception;

public class AddressNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AddressNotFoundException(int customerId) {
		super("Address not found");
    }

}
