package com.base22.rest.tutorial.domain.repository.jpa;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called customerRepository
// CRUD refers Create, Read, Update, Delete

// The CrudRepository<> is taking the Class and the type of the primary key,
// so it knows which class its going to index and how
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    // Auto implemented functions:
    // save
    // saveAll
    // findOne
    // findAll
    // count
    // delete
    // exists
}