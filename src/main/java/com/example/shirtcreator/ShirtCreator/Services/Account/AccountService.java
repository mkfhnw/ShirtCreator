package com.example.shirtcreator.ShirtCreator.Services.Account;

import com.example.shirtcreator.ShirtCreator.Persistence.*;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

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

    HashMap<String, Integer> tokenMap = new HashMap<>();

    // ------------------------------------------------------------------------------------------------- Mappings
    @GetMapping(path = "/api/account/{token}", produces = "application/json")
    public Account getAccount(@PathVariable String token) {
        Optional<Account> accountOptional = accountRepository.findAccountByToken(token);
        if(accountOptional.isPresent()) {
            logger.info("Retrieved account with token: " + token);
            return accountOptional.get();
        } else {
            logger.error("Could not retrieve account with token: " + token);
            return null;
        }

    }

    @PostMapping(path = "/api/account/", produces = "application/json")
    public Account createAccount(@RequestBody MessageNewAccount requestBody) {

        // Create new account object
        Account account = new Account();

        // Check whether costumer already exists - create new one if not existent yet
        Optional<Customer> customer = customerRepository.findCustomerByEmail(requestBody.geteMail());
        if(customer.isPresent()) {
            logger.info("Customer already existing with email " + requestBody.geteMail());

            // Abort if customer already has account
            if(accountRepository.existsById(customer.get().getid())) {
                logger.info("Customer already has account - abort account creation.");
                return null;
            }

            account.setCustomer(customer.get());
        } else {
            Address newAddress = new Address(requestBody.getStreet(), requestBody.getPlz(), requestBody.getLocation());
            Customer newCustomer = new Customer(requestBody.getFirstName(), requestBody.getLastName(), requestBody.geteMail(), newAddress);
            addressRepository.save(newAddress); // Important: Save to db, otherwise we'll throw an error
            customerRepository.save(newCustomer); // Important: Save to db, otherwise we'll throw an error
            account.setCustomer(newCustomer);
            logger.info("New Customer created");
        }


        // Only store hashed password. l33t s3cUritY.
        String passwordHash = Hashing.sha256().hashString(requestBody.getPassword(), StandardCharsets.UTF_8).toString();
        account.setPassword(passwordHash);

        // Generate random token so user is directly logged in
        String token = this.generateLoginToken();
        account.setToken(token);

        // Save to db
        Account savedAccount = accountRepository.save(account);
        logger.info("New account created.");

        // Make entry in loginMap
        this.tokenMap.put(savedAccount.getToken(), savedAccount.getId());

        return account;
    }

    @PutMapping(path = "/api/account/login", produces = "application/json")
    public MessageToken login(@RequestBody MessageLogin requestBody) {

        // We don't save the e-mail on account-level, but on customer level - so we need to get the corresponding customer first
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(requestBody.geteMail());

        // If customer doesn't exist, abort login
        if(customerOptional.isEmpty()) {
            logger.error("Could not find customer with e-mail: " + requestBody.geteMail());
            MessageToken messageToken = new MessageToken();
            messageToken.setToken("");
            return messageToken;
        }

        // Otherwise, get account based on customerID (as it is the foreign key in tbl_Account)
        Customer customer = customerOptional.get();
        Optional<Account> accountOptional = accountRepository.findById(customer.getid());
        if(accountOptional.isEmpty()) {
            logger.error("Could not find account with ID: " + customer.getid());
            MessageToken messageToken = new MessageToken();
            messageToken.setToken("");
            return messageToken;
        }

        // Check whether account is already logged in
        Account account = accountOptional.get();
        if(account.getToken() != null && this.tokenMap.containsKey(account.getToken())) {
            logger.info("Account with ID " + account.getId() + " already logged in.");
            MessageToken response = new MessageToken();
            response.setToken(account.getToken());
            return response;
        }

        // Otherwise "log in" account (if password hashes match)
        String passwordHash = Hashing.sha256().hashString(requestBody.getPassword(), StandardCharsets.UTF_8).toString();
        if(account.getPassword().equals(passwordHash)) {
            String token = this.generateLoginToken();
            account.setToken(token);
            this.tokenMap.put(token, account.getId());
            accountRepository.save(account);
            logger.info("Account with ID " + account.getId() + " and token " + account.getToken() + " successfully logged in.");
            MessageToken messageToken = new MessageToken();
            messageToken.setToken(account.getToken());
            return messageToken;
        }

        // If we get here, we got a password mismatch
        logger.error("Could not log in account due to password mismatch.");
        MessageToken messageToken = new MessageToken();
        messageToken.setToken("");
        return messageToken;

    }

    @PutMapping(path = "/api/account/logout", produces = "text/plain")
    public String logout(@RequestBody MessageToken requestBody) {

        // Logout user if token is valid
        if(this.tokenMap.containsKey(requestBody.getToken())) {
            Optional<Account> accountOptional = accountRepository.findAccountByToken(requestBody.getToken());
            if(accountOptional.isEmpty()) {
                logger.error("Invalid token for logout: " + requestBody.getToken());
                return "false";
            }
            Account account = accountOptional.get();
            account.setToken("");
            accountRepository.save(account);
            this.tokenMap.remove(requestBody.getToken());
            logger.info("Logged out account with token: " + requestBody.getToken());
            return "true";
        }

        // If we get here, something went wrong
        logger.error("Could not log out account with token: " + requestBody.getToken());
        return "false";

    }

    // @DeleteMapping   <- I'm sure we don't want front-end users to randomly delete accounts...

    // ------------------------------------------------------------------------------------------------- Helper methods
    // TODO: Put method generateLoginToken in a new class (e.g., AccountVerification) in Business folder
    public String generateLoginToken() {

        // Generate new random string based on random ints & make sure it's not used yet
        String token = "";
        Random random = new Random();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        do {
            token = Hashing.sha256().hashString(random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString(), StandardCharsets.UTF_8).toString();
        } while (this.tokenMap.containsKey(token));

        return token;
    }

}
