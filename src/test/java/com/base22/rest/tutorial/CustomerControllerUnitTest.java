//package com.base22.rest.tutorial;
//
//import com.base22.rest.tutorial.domain.model.jpa.Customer;
//import com.base22.rest.tutorial.domain.service.CustomerService;
//import com.base22.rest.tutorial.web.CustomerController;
//import io.restassured.RestAssured;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.annotation.PostConstruct;
//import java.util.Arrays;
//import java.util.List;
//
//import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//// How does this work???
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class CustomerControllerUnitTest {
//
//    @LocalServerPort
//    private int port;
//
//    private String uri;
//
//    @PostConstruct
//    public void init() {
//        uri = "http://localhost:" + port;
//    }
//
//    @MockBean
//    private CustomerService customerService;
//
//    @Test
//    public void getAllCustomersTest() throws Exception {
//        Customer customer1 = new Customer("Test", "test@test", "tester", "password");
//        Customer customer2 = new Customer("Tester", "test2@test", "tester2", "password");
//        List<Customer> Customers = Arrays.asList(customer1, customer2);
//
//        // When the getAllCustomers method is hit, provide the Customer array
//        when(customerService.getAllCustomers()).thenReturn(Customers);
//
//        mvc.perform( MockMvcRequestBuilders
//            .get("/v1/customers")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(2)));
//    }
//
//    @Test
//    public void getCustomerByIdTest() throws Exception {
//        Customer alex = new Customer("Alex Test", "alex@test.com", "alextest", "Test123");
//
////        // How does this given call work?
////        given(customerService.getCustomerById(1L)).willReturn(alex);
////
////        mvc.perform(get("/v1/customers/1")
////            .contentType(MediaType.APPLICATION_JSON))
////            .andExpect(status().isOk())
////            .andExpect(jsonPath("$", hasSize(1)))
////            .andExpect(jsonPath("$.username", is(alex.getUsername())));
//
//        RestAssured.given().
//                    param("Customer", alex).
//                when().
//                    get("localhost:8080/v1/customers/1")
//                .then()
//                    .statusCode(200)
//                    .body("name", equalTo("Alex Test"));
//    }
//}
