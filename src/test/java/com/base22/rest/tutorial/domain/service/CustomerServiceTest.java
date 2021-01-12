package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.model.jpa.CustomerNotFoundException;
import com.base22.rest.tutorial.domain.model.jpa.UsernameNotValidException;
import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import com.base22.rest.tutorial.provider.LocalDateTimeProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

  private final LocalDateTime mockedDate = LocalDateTime.of(2020, Month.JULY, 29, 19, 30, 40);

  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private LocalDateTimeProvider localDateTimeProvider;

  @InjectMocks
  private CustomerService customerService;

  @BeforeEach
  public void setup() {
    //LocalDateTimeProvider will return July 29, 2020 19:30:40
    given(localDateTimeProvider.now()).willReturn(mockedDate);
  }

  //TODO: Create a customer

  @Test
  @DisplayName("Given valid parameters, service will create a customer")
  public void given_valid_parameters_service_will_create_a_customer() {

    // arrange
    Customer expectedCustomer = new Customer("John", "john_wick@mail.com", "JohnWick", "passwordHash_32186461321987",
        localDateTimeProvider.now(), localDateTimeProvider.now());
    given(bCryptPasswordEncoder.encode("password")).willReturn("passwordHash_32186461321987");
    given(customerRepository.save(any(Customer.class))).willAnswer(returnsFirstArg());

    // act
    Customer createdCustomer = customerService.generate("John", "john_wick@mail.com", "JohnWick", "password");

    // assert
    assertThat(createdCustomer).isEqualTo(expectedCustomer);

  }

  @Test
  @DisplayName("Given repeated parameters, service will return a 409")
  public void given_repeated_parameters_service_will_return_a_409() {
    //arrange
    Customer customerOnDB =  new Customer("John", "john_wick@mail.com", "JohnWick", "passwordHash_32186461321987",
            localDateTimeProvider.now(), localDateTimeProvider.now());
    given(customerRepository.save(customerOnDB)).willReturn(customerOnDB);
    given(
      customerService.generate("Juan", "john_wick@mail.com", "JohnWick", "passwordHash_32186461321987")
    ).willThrow(UsernameNotValidException.class);

    //act & assert
    Assertions.assertThrows(UsernameNotValidException.class, () -> {
      customerService.generate("Juan", "john_wick@mail.com", "JohnWick", "passwordHash_32186461321987");
    });
  }

  @Test
  @DisplayName("Given valid customer id, should return expected customer")
  public void given_valid_customer_id_should_return_expected_customer() {

    //arrange
    //The valid id = 43
    Customer expectedCustomer = new Customer("John", "john_wick@mail.com", "JohnWick", "passwordHash_32186461321987",
        localDateTimeProvider.now(), localDateTimeProvider.now());
    given(customerRepository.findById(43L)).willReturn(java.util.Optional.of(expectedCustomer));

    //act
    Customer resultCustomer = customerService.getCustomerById(43L);

    //assert
    assertThat(resultCustomer).isEqualTo(expectedCustomer);
  }

  @Test
  @DisplayName("Given invalid customer id, should throw CustomerNotFoundException")
  public void given_invalid_customer_id_should_throw_customer_not_found_exception() {

    //arrange
    given(customerRepository.findById(69L)).willThrow(new CustomerNotFoundException(69L));

    //act & assert
    Assertions.assertThrows(CustomerNotFoundException.class, () -> {
      customerService.getCustomerById(69L);
    });
  }

  @Test
  @DisplayName("When existing customers it will return all")
  public void when_existing_customers_it_will_return_all() {

    //arrange
    Customer customer1 = new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
        localDateTimeProvider.now());
    Customer customer2 =
        new Customer("Daniel", "daniel@mail.com", "daniel123", "password1", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    List<Customer> customers = Arrays.asList(customer1, customer2);
    given(customerRepository.findAll()).willReturn(customers);

    //act
    List<Customer> resultOfCustomers = customerService.getAllCustomers();

    //assert
    assertThat(resultOfCustomers).isEqualTo(customers);

  }

  @Test
  @DisplayName("When no customer exist, it will return an empty list")
  public void when_no_customer_exist_it_will_return_an_empty_list() {

    //arrange
    given(customerRepository.findAll()).willReturn(new ArrayList<>());

    //act
    List<Customer> resultOfCustomers = customerService.getAllCustomers();

    //assert
    assertThat(resultOfCustomers).isEqualTo(new ArrayList<>());

  }

  @Test
  @DisplayName("When updating a customer it will save it")
  public void when_updating_a_customer_it_will_save_it() {

    //arrange
    Customer expectedCustomer =
        new Customer("John", "john_wick@mail.com", "JohnWick", "password", localDateTimeProvider.now(),
            localDateTimeProvider.now());
    Customer customerToSave = new Customer("John", "john_wick@mail.com", "JohnWick", "password", null, null);

    given(customerRepository.save(any(Customer.class))).willReturn(expectedCustomer);

    //act
    Customer resultCustomer = customerService.saveCustomer(customerToSave);

    //assert
    assertThat(resultCustomer).isEqualTo(expectedCustomer);
  }

  //TODO: Delete all customers

  //TODO: Delete customer by id
}
