package com.base22.rest.tutorial.domain.repository.jpa;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete

// The JpaRepository<> is taking the Class and the type of the primary key,
// so it knows which class its going to index and how
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Auto implemented functions:
    // save
    // saveAll
    // findOne
    // findAll
    // count
    // delete
    // exists

    Customer findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
}