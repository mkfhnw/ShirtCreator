package com.example.shirtcreator.ShirtCreator.Services.Account;

import com.example.shirtcreator.ShirtCreator.Persistence.Account;
import com.example.shirtcreator.ShirtCreator.Persistence.AccountRepository;
import com.example.shirtcreator.ShirtCreator.Persistence.Customer;
import com.example.shirtcreator.ShirtCreator.Persistence.CustomerRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class AccountService {

    // Fields
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;


    // Mappings
    @GetMapping(path = "/api/account/{id}", produces = "application/json")
    public Account getAccount(@PathVariable Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @PostMapping(path = "/api/account/", produces = "application/json")
    public Account createAccount(@RequestBody MessageNewAccount requestBody) {

        // Create new account object
        Account account = new Account();

        // Grab customer based on ID - if customer doesn't exist we abort
        Optional<Customer> customer = customerRepository.findById(requestBody.getCustomerId());
        if(!customer.isPresent()) { return null; }
        account.setCustomer(customer.get());

        // The password already comes hashed - we double-hash it though. l33t s3cUritY.
        String passwordHash = Hashing.sha256().hashString(requestBody.getPassword(), StandardCharsets.UTF_8).toString();
        account.setPassword(passwordHash);

        // Save to db
        accountRepository.save(account);

        return account;
    }

    // @PutMapping      <- We don't have the opportunity to change passwords atm, so no need for puts

    // @DeleteMapping   <- I'm sure we don't want front-end users to randomly delete accounts...

}
