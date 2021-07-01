package com.restapipractice.restapi.exception;

public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {
		super("Customer database is empty");
    }
}