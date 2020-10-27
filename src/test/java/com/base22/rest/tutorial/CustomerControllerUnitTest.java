package com.base22.rest.tutorial;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.service.CustomerService;
import com.base22.rest.tutorial.web.CustomerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// How does this work???
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void getAllCustomersTest() throws Exception {
        Customer customer1 = new Customer("Test", "test@test", "tester", "password");
        Customer customer2 = new Customer("Tester", "test2@test", "tester2", "password");
        List<Customer> Customers = Arrays.asList(customer1, customer2);

        // When the getAllCustomers method is hit, provide the Customer array
        when(customerService.getAllCustomers()).thenReturn(Customers);

        mvc.perform( MockMvcRequestBuilders
            .get("/v1/customers")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        Customer alex = new Customer("Alex Test", "alex@test.com", "alextest", "Test123");

        // How does this given call work?
        given(customerService.getCustomerById(1L)).willReturn(alex);

        mvc.perform(get("/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1))) // Why is this not working?
            .andExpect(jsonPath("$.username", is(alex.getUsername())));
    }
}
