package com.base22.rest.tutorial.domain.model.jpa;


// this status may change if needed, reasons for choosing 409 Conflict below
// https://stackoverflow.com/a/3826024
public class EmailNotValidException extends RuntimeException {
  public EmailNotValidException(String email) {
    super("Customer with email " + email + " is not valid" + "\nPlease choose another one.");
  }
}
