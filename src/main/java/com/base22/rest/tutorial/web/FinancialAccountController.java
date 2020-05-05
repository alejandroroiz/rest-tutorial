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

    private FinancialAccountService financialAccountService;


    public FinancialAccountController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String description, @RequestParam long balance) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        financialAccountService.generate(name, description, balance);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<FinancialAccount> getAllUsers() {
        return financialAccountService.getAllAccounts();
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateAccountBalance(@RequestParam Integer id, @RequestParam long balance) {
        FinancialAccount financialAccount = financialAccountService.getAccount( id );

        if (financialAccount != null) {
            financialAccount.setBalance( balance );

            return financialAccountService.saveAccount( financialAccount ).toString();
        } else {
            return "FinancialAccount could not be found with that id";
        }
    }
}
