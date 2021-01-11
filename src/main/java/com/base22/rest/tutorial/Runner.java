package com.base22.rest.tutorial;

import com.base22.rest.tutorial.domain.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    @Autowired
    private CustomerService service;

    @Override
    public void run(String... args) throws Exception {
        service.deleteAllCustomers();

        service.generate("Alex", "alex@test.com", "alejandro", "alex123");
        service.generate("Diego", "diego@test.com", "diego", "diego123");
    }
}
