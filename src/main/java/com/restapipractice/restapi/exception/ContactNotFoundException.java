package com.restapipractice.restapi.exception;

public class ContactNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ContactNotFoundException(int customerId) {
		super("Contact not found");
    }

}
