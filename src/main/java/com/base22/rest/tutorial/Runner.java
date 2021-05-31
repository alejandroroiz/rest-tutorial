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
	private static final String ENVIRONMENT_JVM_PROPERTY = "REST_TUTORIAL_ENVIRONMENT";

	@Autowired
	private CustomerService service;

	@Override
	public void run(String... args) throws Exception {
		String environment = System.getenv(ENVIRONMENT_JVM_PROPERTY);

		if (environment.startsWith("LOCAL")) {
			logger.info("[Rest Tutorial] Running CommandLineRunner -- Creating dummy data");
			service.deleteAllCustomers();

			service.generate("Alex", "alex@test.com", "alejandro", "alex123");
			service.generate("Diego", "diego@test.com", "diego", "diego123");
		}
	}
}