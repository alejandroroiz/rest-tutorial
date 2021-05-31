package com.base22.rest.tutorial.exception.model;

public class EmailNotValidException extends RuntimeException {
	public EmailNotValidException(String email) {
		super("Customer with email " + email + " is not valid" + "\nPlease choose another one.");
	}
}