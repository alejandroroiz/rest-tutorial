package com.base22.rest.tutorial;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.service.CustomerService;
import com.base22.rest.tutorial.web.CustomerController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        Customer c = new Customer();
        c.setName("Test");
        c.setEmail("test@test.com");
        c.setUsername("test");
        c.setPassword("Test123");

        when(customerService.generate("Test", "test@test.com", "test", "Test123")).thenReturn(c);

        Customer customer = customerController.createNewCustomer(new Customer("Test", "test@test.com", "test", "Test123"));

        verify(customerService).generate("Test", "test@test.com", "test", "Test123");

        assertThat(c, is(customer));
    }


    @Test
    public void testGetAllCustomers() {

    }

    @Test
    public void testDeleteAllCustomers() {

    }

    @Test
    public void testGetCustomerById(){
        Customer c = new Customer();
        c.setId(1L);

        when(customerService.getCustomerById(1L)).thenReturn(c);

        Customer customer = customerController.getCustomerById(1L);

        verify(customerService).getCustomerById(1L);

        //assertEquals(1L, customer.getId().longValue());
        assertThat(customer.getId(), is(1L));
    }

    @Test
    public void testDeleteCustomerById() {

    }


    @Test
    public void testUpdateCustomerById() {

    }
}
