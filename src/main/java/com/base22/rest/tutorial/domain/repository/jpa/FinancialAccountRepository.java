package com.base22.rest.tutorial.domain.repository.jpa;

import com.base22.rest.tutorial.domain.model.jpa.FinancialAccount;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called financialAccountRepository
// CRUD refers Create, Read, Update, Delete

public interface FinancialAccountRepository extends CrudRepository<FinancialAccount, Integer> {

}