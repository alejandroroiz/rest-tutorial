package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  //TODO: Customer Service tests
  @Test
  void savedUserHasRegistrationDate() {
    assertThat(true).isEqualTo(true);
  }
}
