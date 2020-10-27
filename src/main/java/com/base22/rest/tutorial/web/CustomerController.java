package com.base22.rest.tutorial.web;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/v1") // This means URL's start with /v1 (after Application path)
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // READ ALL
    @GetMapping(path="/customers")
    // Returns an iterable object of Customers provided by the Service
    public @ResponseBody List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // CREATE
    @PostMapping(path="/customers") // Map ONLY POST Requests
    public @ResponseBody Customer createNewCustomer (@RequestBody Customer customer) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return customerService.generate(
            customer.getName(), customer.getEmail(), customer.getUsername(),
            customer.getPassword());

    }


    // DELETE ALL
    @DeleteMapping(path="/customers") // Maps to DELETE Requests
    public void deleteAllCustomers() {
        // Add some type of validation maybe?
        customerService.deleteAllCustomers();
    }

    // READ ONE
    @GetMapping(path="/customers/{customerId}")
    public @ResponseBody Customer getCustomerById(
     @PathVariable("customerId") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    // UPDATE ONE
    @PutMapping(path="/customers/{customerId}")
    public @ResponseBody Customer updateCustomerById(
        @PathVariable("customerId") Long customerId, @RequestBody Customer newCustomerData) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById( customerId );

        if (customer != null) {

            customer.setName( newCustomerData.getName() );
            customer.setEmail( newCustomerData.getEmail() );
            customer.setUsername( newCustomerData.getUsername() );
            customer.setPassword( newCustomerData.getPassword() );

            return customerService.saveCustomer(customer);
        } else {
            throw new CustomerNotFoundException(customerId);
        }
    }

    // DELETE ONE
    @DeleteMapping(path="/customers/{customerId}")
    public @ResponseBody Customer deleteCustomerById(
     @PathVariable("customerId") Long customerId) throws CustomerNotFoundException {

        return customerService.deleteCustomerById( customerId );
    }
}
