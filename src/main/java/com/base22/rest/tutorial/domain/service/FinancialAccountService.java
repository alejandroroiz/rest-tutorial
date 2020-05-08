package com.base22.rest.tutorial.domain.service;

import com.base22.rest.tutorial.domain.model.jpa.FinancialAccount;
import com.base22.rest.tutorial.domain.repository.jpa.FinancialAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialAccountService {

    final FinancialAccountRepository repository;

    @Autowired
    public FinancialAccountService(FinancialAccountRepository repository) {
        this.repository = repository;
    }

    // Create a new Financial Account
    public FinancialAccount generate(String name, String description, long balance) {

        FinancialAccount financialAccount = new FinancialAccount();

        financialAccount.setName(name);
        financialAccount.setDescription(description);
        financialAccount.setBalance(balance);

        return repository.save(financialAccount);
    }

    // Get a FinancialAccount by Id
    public FinancialAccount getAccount(Integer id) {
        return repository.findById( id ).orElse( null );
    }

    // Get all FinancialAccounts
    public Iterable<FinancialAccount> getAllAccounts() {
        return repository.findAll();
    }

    // Save a Financial Account
    // Typically used after updates
    public FinancialAccount saveAccount(FinancialAccount financialAccount) {
        return repository.save(financialAccount);
    }
}
