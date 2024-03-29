package com.base22.rest.tutorial.web;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/v1") // This means URL's start with /v1 (after Application path)
@Api(value = "Customers",
    tags = {"Customers"})
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  // READ ALL
  @GetMapping(path = "/customers")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Returns a list of customers provided by the service",
      tags = {"Customers"})
  public @ResponseBody
  List<Customer> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  // CREATE
  @PostMapping(path = "/customers") // Map ONLY POST Requests
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a new user",
      tags = {"Customers"})
  public @ResponseBody
  Customer createNewCustomer(@Valid @RequestBody Customer customer) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    return customerService
            .generate(customer.getName(), customer.getEmail(), customer.getUsername(), customer.getPassword());
  }

  // DELETE ALL
  @DeleteMapping(path = "/customers") // Maps to DELETE Requests
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete all customers",
      tags = {"Customers"})
  public void deleteAllCustomers() {
    // Add some type of validation maybe?
    customerService.deleteAllCustomers();
  }

  // READ ONE
  @GetMapping(path = "/customers/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a customer by id",
      tags = {"Customers"})
  public @ResponseBody
  Customer getCustomerById(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
    return customerService.getCustomerById(customerId);
  }

  // UPDATE ONE
  @PutMapping(path = "/customers/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Update a customer by id",
      tags = {"Customers"})
  public @ResponseBody
  Customer updateCustomerById(@PathVariable("customerId") Long customerId, @Valid @RequestBody Customer newCustomerData)
      throws CustomerNotFoundException {
    // Use this line to verify if the customer exists as an exception is thrown if not found
    customerService.getCustomerById(customerId);

    return customerService.updateCustomer(customerId, newCustomerData.getName(), newCustomerData.getEmail(), newCustomerData.getUsername(), newCustomerData.getPassword());

  }

  // DELETE ONE
  @DeleteMapping(path = "/customers/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a customer by id",
      tags = {"Customers"})
  public @ResponseBody
  Customer deleteCustomerById(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException {

    return customerService.deleteCustomerById(customerId);
  }
}
