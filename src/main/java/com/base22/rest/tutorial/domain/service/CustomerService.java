package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotValidException;
import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import com.base22.rest.tutorial.provider.LocalDateTimeProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomerRepository repository;
  private final LocalDateTimeProvider localDateTimeProvider;

  public CustomerService(CustomerRepository repository, BCryptPasswordEncoder encoder,
      LocalDateTimeProvider localDateTimeProvider) {
    this.bCryptPasswordEncoder = encoder;
    this.repository = repository;
    this.localDateTimeProvider = localDateTimeProvider;
  }

  // Check if a new customer entry already exists
  private boolean isValid(Customer customer) {

    if ( repository.existsByEmailIgnoreCase(customer.getEmail()) || repository.existsByUsernameIgnoreCase(customer.getUsername())) {
      return false;
    }
    return true;
  }

  // Create a new Customer
  public Customer generate(String name, String email, String username, String password) throws CustomerNotValidException {
    String encodedPassword = bCryptPasswordEncoder.encode(password);
    LocalDateTime now = localDateTimeProvider.now();

    Customer customer = new Customer(name, email, username, encodedPassword, now, now);
    if (isValid(customer)) {
      return repository.save(customer);
    } else {
      // Probably might be better to separate into two different exceptions to have a clearer response message
      throw new CustomerNotValidException(customer);
    }
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
    customer.setLastUpdatedDate(localDateTimeProvider.now());
    return repository.save(customer);
  }

  // Delete all Customers
  public void deleteAllCustomers() {
    repository.deleteAll();
  }

  // Delete a customer by Id
  public Customer deleteCustomerById(Long id) {
    Customer customer = getCustomerById(id);
    repository.deleteById(id);
    return customer;
  }
}
