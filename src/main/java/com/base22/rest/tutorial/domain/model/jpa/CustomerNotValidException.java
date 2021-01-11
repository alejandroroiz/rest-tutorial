package com.base22.rest.tutorial.domain.model.jpa;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// this status may change if needed, reasons for choosing 409 Conflict below
// https://stackoverflow.com/a/3826024
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerNotValidException extends RuntimeException {
    public CustomerNotValidException(Customer customer) {
        super("Customer with username " + customer.getUsername() + " and with email " + customer.getEmail() + " is not valid" +
                "\nPlease choose another one.");
    }
}
