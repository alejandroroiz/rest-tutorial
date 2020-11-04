package com.base22.rest.tutorial.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class HelloControllerTest {

  private MockMvc mvc;

  @InjectMocks
  private HelloController helloController;

  @BeforeEach
  public void setup() {
    // MockMvc standalone approach
    mvc = MockMvcBuilders.standaloneSetup(helloController).build();
  }

  @Test
  @DisplayName("Hello Controller")
  public void testHelloController() throws Exception {

    // arrange

    // act
    MockHttpServletResponse response =
        mvc.perform(get("/").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo("Hello Base22. This is a message from Spring!");
  }
}
