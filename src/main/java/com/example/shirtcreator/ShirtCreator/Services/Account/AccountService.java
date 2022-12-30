package com.example.shirtcreator.ShirtCreator.Services.Account;

import com.example.shirtcreator.ShirtCreator.Business.CustomerVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.*;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    Logger logger = LoggerFactory.getLogger(AccountService.class);

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

        // Check whether costumer already exists - create new one if not existent yet
        Optional<Customer> customer = customerRepository.findCustomerByEmail(requestBody.getEmail());
        if(customer.isPresent()) {
            logger.info("Customer already existing with email " + requestBody.getEmail());

            // Abort if customer already has account
            if(accountRepository.existsById(customer.get().getid())) {
                logger.info("Customer already has account - abort account creation.");
                return null;
            }

            account.setCustomer(customer.get());
        } else {
            Address newAddress = new Address(requestBody.getStreet(), requestBody.getPlz(), requestBody.getLocation());
            Customer newCustomer = new Customer(requestBody.getFirstName(), requestBody.getLastName(), requestBody.getEmail(), newAddress);
            addressRepository.save(newAddress); // Important: Save to db, otherwise we'll throw an error
            customerRepository.save(newCustomer); // Important: Save to db, otherwise we'll throw an error
            account.setCustomer(newCustomer);
            logger.info("New Customer created");
        }


        // Only store hashed password. l33t s3cUritY.
        String passwordHash = Hashing.sha256().hashString(requestBody.getPassword(), StandardCharsets.UTF_8).toString();
        account.setPassword(passwordHash);

        // Save to db
        accountRepository.save(account);
        logger.info("New account created.");
        return account;
    }

    // @PutMapping      <- We don't have the opportunity to change passwords atm, so no need for puts

    // @DeleteMapping   <- I'm sure we don't want front-end users to randomly delete accounts...

}
