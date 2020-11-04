package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  // Create a new Customer
  public Customer generate(String name, String email, String username, String password) {

    Customer customer = new Customer(name, email, username, password);

    return repository.save(customer);
  }

  // Get a Customer by Id
  public Customer getCustomerById(Long id) {
    return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
  }

  // Get all Customers
  public List<Customer> getAllCustomers() {
    return repository.findAll();
  }

  // Save a Customer
  // Typically used after updates
  public Customer saveCustomer(Customer customer) {
    return repository.save(customer);
  }

  // Delete all Customers
  public void deleteAllCustomers() {
    repository.deleteAll();
  }

  // Delete a customer by Id
  public Customer deleteCustomerById(Long id) {
    Customer customer = getCustomerById(id);
    if (customer != null) {
      repository.deleteById(id);
      return customer;
    } else {
      throw new CustomerNotFoundException(id);
    }
  }

  // login user
  public void login (String username, String password) {
    Customer customer = repository.findByUsername(username);
    if ( customer != null) {
      if (customer.getPassword().equals(password)) {

      }
    } else {
      throw new CustomerNotFoundException(username);
    }
  }
}
