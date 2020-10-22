package com.base22.rest.tutorial.web;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController // This means that this class is a Controller
@RequestMapping(path="/v1") // This means URL's start with /v1 (after Application path)
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE
    @PostMapping(path="/customers") // Map ONLY POST Requests
    public @ResponseBody String addCustomer (@RequestParam String name, @RequestParam String email,
        @RequestParam String username, @RequestParam String password,  @RequestParam String dob) throws ParseException {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth = formatter.parse( dob );

        customerService.generate(name, email, username, password, dateOfBirth);
        return "Saved";
    }

    // READ
    @GetMapping(path="/customers")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // UPDATE
    @PutMapping(path="/customers/{customer-id}")
    public @ResponseBody String updateCustomer(@RequestParam Integer id, @RequestParam String name,
       @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam Date dob) {
        Customer customer = customerService.getCustomer( id );

        if (customer != null) {
            customer.setName( name );
            customer.setEmail( email );
            customer.setUsername( username );
            customer.setPassword( password );
            customer.setDob( dob );

            return customerService.saveCustomer(customer).toString();
        } else {
            return "FinancialAccount could not be found with that id";
        }
    }
}
