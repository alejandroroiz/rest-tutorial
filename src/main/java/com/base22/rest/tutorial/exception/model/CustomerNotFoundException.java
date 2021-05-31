package com.base22.rest.tutorial.exception.model;

public class CustomerNotFoundException extends RuntimeException {
	public CustomerNotFoundException(Long id) {
		super("Could not find customer with id " + id);
	}

	public CustomerNotFoundException(String username) {
		super("Could not find customer with username " + username);
	}
}