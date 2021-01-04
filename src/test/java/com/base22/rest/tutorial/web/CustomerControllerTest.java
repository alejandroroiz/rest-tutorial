package com.base22.rest.tutorial.web;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.service.CustomerService;
import com.base22.rest.tutorial.provider.LocalDateTimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerControllerTest {

  private MockMvc mvc;
  private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final LocalDateTime now = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40);

  @Mock
  private CustomerService customerService;

  @Mock
  private LocalDateTimeProvider localDateTimeProvider;

  @InjectMocks
  private CustomerController customerController;

  @BeforeEach
  public void setup() {
    //LocalDateTimeProvider will return July 29, 2020 19:30:40
    given(localDateTimeProvider.now()).willReturn(now);

    // MockMvc standalone approach
    mvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  @Test
  @DisplayName("When no customers available, get all customers should be empty")
  public void when_no_customers_available_get_all_customers_should_be_empty() throws Exception {

    // arrange
    given(customerService.getAllCustomers()).willReturn(new ArrayList<>());

    // act
    MockHttpServletResponse response =
        mvc.perform(get("/v1/customers/").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo("[]");
  }

  @Test
  @DisplayName("When customers available, get all customers should not be empty")
  public void when_customers_available_get_all_customers_should_not_be_empty() throws Exception {

    // arrange
    Customer customer1 = new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
        localDateTimeProvider.now());
    Customer customer2 =
        new Customer("Daniel", "daniel@mail.com", "daniel123", "password1", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    List<Customer> customers = Arrays.asList(customer1, customer2);
    given(customerService.getAllCustomers()).willReturn(customers);

    // act
    MockHttpServletResponse response =
        mvc.perform(get("/v1/customers/").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(OBJECT_MAPPER.writeValueAsString(customers));
  }


  @Test
  @DisplayName("Given valid customer data should create a new customer")
  public void given_valid_customer_should_create_new_customer() throws Exception {

    // arrange
    Customer expectedCustomer =
        new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    given(customerService.generate("John", "john_wick@mail.com", "JohnWick", "password")).willReturn(expectedCustomer);

    // act
    MockHttpServletResponse response = mvc.perform(post("/v1/customers/").contentType(MediaType.APPLICATION_JSON)
        .content(OBJECT_MAPPER.writeValueAsString(expectedCustomer))).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentAsString()).isEqualTo(OBJECT_MAPPER.writeValueAsString(expectedCustomer));

  }

  @Test
  @DisplayName("Given invalid customer data should not create a customer, and return bad request")
  public void given_invalid_customer_should_not_create_customer_and_return_bad_request() throws Exception {

    // arrange
    Customer customerToCreate =
        new Customer("John", null, "JohnWick", "password", localDateTimeProvider.now(), localDateTimeProvider.now());

    // act
    MockHttpServletResponse response = mvc.perform(post("/v1/customers/").contentType(MediaType.APPLICATION_JSON)
        .content(OBJECT_MAPPER.writeValueAsString(customerToCreate))).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

  }

  //TODO: DELETE ALL CUSTOMERS

  @Test
  @DisplayName("Given existing customer id should return customer with given id")
  public void given_existing_customer_id_should_return_customer_with_given_id() throws Exception {

    // arrange
    Customer expectedCustomer =
        new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    expectedCustomer.setId(18L);
    given(customerService.getCustomerById(18L)).willReturn(expectedCustomer);

    // act
    MockHttpServletResponse response =
        mvc.perform(get("/v1/customers/18").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(OBJECT_MAPPER.writeValueAsString(expectedCustomer));

  }

  @Test
  @DisplayName("Given non existing customer id should return 404 code")
  public void given_non_existing_customer_id_should_return_404_code() throws Exception {

    // arrange
    given(customerService.getCustomerById(420L)).willThrow(new CustomerNotFoundException(420L));

    // act
    MockHttpServletResponse response =
        mvc.perform(get("/v1/customers/420").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

  }

  @Test
  @DisplayName("Given existing customer id should update customer with given id")
  public void given_existing_customer_id_should_update_customer_with_given_id() throws Exception {

    // arrange
    Customer customerOnDB =
        new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    customerOnDB.setId(18L);
    Customer expectedCustomer =
        new Customer("Juan", "juan_peluca@mail.com", "JuanPeluca", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    expectedCustomer.setId(18L);
    given(customerService.getCustomerById(18L)).willReturn(customerOnDB);
    given(customerService.saveCustomer(expectedCustomer)).willReturn(expectedCustomer);

    // act
    MockHttpServletResponse response = mvc.perform(put("/v1/customers/18").contentType(MediaType.APPLICATION_JSON)
        .content(OBJECT_MAPPER.writeValueAsString(expectedCustomer))).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(OBJECT_MAPPER.writeValueAsString(expectedCustomer));

  }

  @Test
  @DisplayName("Given non existing customer id while updating an user should return 404 code")
  public void given_non_existing_customer_id_while_updating_an_user_should_return_404_code() throws Exception {

    // arrange
    Customer expectedCustomer =
        new Customer("Juan", "juan_peluca@mail.com", "JuanPeluca", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    expectedCustomer.setId(420L);
    given(customerService.getCustomerById(420L)).willThrow(new CustomerNotFoundException(420L));

    // act
    MockHttpServletResponse response = mvc.perform(put("/v1/customers/420").contentType(MediaType.APPLICATION_JSON)
        .content(OBJECT_MAPPER.writeValueAsString(expectedCustomer))).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Given not valid customer return bad request")
  public void given_not_valid_customer_return_bad_request() throws Exception {

    // arrange
    Customer expectedCustomer =
        new Customer("Juan", null, "JuanPeluca", "password", localDateTimeProvider.now(), localDateTimeProvider.now());

    // act
    MockHttpServletResponse response = mvc.perform(put("/v1/customers/420").contentType(MediaType.APPLICATION_JSON)
        .content(OBJECT_MAPPER.writeValueAsString(expectedCustomer))).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  // TODO: DELETE A CUSTOMER

}
