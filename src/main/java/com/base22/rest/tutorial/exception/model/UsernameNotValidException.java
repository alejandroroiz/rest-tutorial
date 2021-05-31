package com.base22.rest.tutorial.exception.model;

public class UsernameNotValidException extends RuntimeException {
	public UsernameNotValidException(String username) {
		super("Customer with username " + username + " is not valid" + "\nPlease choose another one.");
	}
}