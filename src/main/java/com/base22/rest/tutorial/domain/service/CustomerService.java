package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerService {

    final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // Create a new Customer
    public Customer generate(String name, String email, String username, String password, Date dob) {

        Customer customer = new Customer();

        customer.setName(name);
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setDob(dob);

        return repository.save(customer);
    }

    // Get a Customer by Id
    public Customer getCustomer(Integer id) {
        return repository.findById( id ).orElse( null );
    }

    // Get all Customers
    public Iterable<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // Save a Customer
    // Typically used after updates
    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }
}
