package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.*;
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

  // Create a new Customer
  public Customer generate(String name, String email, String username, String password)
          throws UsernameNotValidException, EmailNotValidException {
    // Verify the username and email are not being repeated.
    if (repository.existsByUsernameIgnoreCase(username)) {
      throw new UsernameNotValidException(username);
    } else if (repository.existsByEmailIgnoreCase(email)) {
      throw new EmailNotValidException(email);
    } else {
      String encodedPassword = bCryptPasswordEncoder.encode(password);
      LocalDateTime now = localDateTimeProvider.now();
      Customer customer = new Customer(name, email, username, encodedPassword, now, now);

      return repository.save(customer);
    }
  }

  // Update a customer
  public Customer updateCustomer(Long customerId, String name, String email, String username, String password) {
    Customer customer = getCustomerById( customerId );

    customer.setName( name );
    customer.setEmail( email );
    customer.setUsername( username );
    customer.setPassword( bCryptPasswordEncoder.encode( password ) );
    customer.setLastUpdatedDate( localDateTimeProvider.now() );

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
