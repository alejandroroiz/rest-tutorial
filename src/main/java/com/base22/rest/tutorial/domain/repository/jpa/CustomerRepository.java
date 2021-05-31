package com.base22.rest.tutorial.domain.repository.jpa;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByUsername(String username);

	boolean existsByUsernameIgnoreCase(String username);
	boolean existsByEmailIgnoreCase(String email);
}