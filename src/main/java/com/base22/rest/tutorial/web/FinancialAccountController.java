package com.base22.rest.tutorial.web;

import com.base22.rest.tutorial.domain.model.jpa.FinancialAccount;
import com.base22.rest.tutorial.domain.repository.jpa.FinancialAccountRepository;
import com.base22.rest.tutorial.domain.service.FinancialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class FinancialAccountController {

    private FinancialAccountService service;

    @Autowired
    public FinancialAccountController(FinancialAccountService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String description, @RequestParam long balance) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        service.generate(name, description, balance);
        return "Saved";
    }

    // READ
    @GetMapping(path="/all")
    public @ResponseBody Iterable<FinancialAccount> getAllUsers() {
        return service.getAllAccounts();
    }

    // UPDATE
    @PutMapping(path="/update")
    public @ResponseBody String updateAccountBalance(@RequestParam Integer id, @RequestParam long balance) {
        FinancialAccount financialAccount = service.getAccount( id );

        if (financialAccount != null) {
            financialAccount.setBalance( balance );

            return service.saveAccount( financialAccount ).toString();
        } else {
            return "FinancialAccount could not be found with that id";
        }
    }
}
