package com.example.shirtcreator.ShirtCreator.Services.Customer;

import com.example.shirtcreator.ShirtCreator.Business.CustomerVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.Address;
import com.example.shirtcreator.ShirtCreator.Persistence.AddressRepository;
import com.example.shirtcreator.ShirtCreator.Persistence.Customer;
import com.example.shirtcreator.ShirtCreator.Persistence.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerVerification customerVerification;
    final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    @GetMapping(path = "/api/customer/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable Integer id) {
        Optional<Customer> c = customerRepository.findById(id);
        return c.orElse(null);
    }

    @PutMapping(path = "/api/customer/{id}", produces = "application/json")
    public boolean updateCustomer(@PathVariable Integer id, @RequestBody MessageNewCustomer m) {
        Optional<Customer> co = customerRepository.findById(id);
        if (co.isEmpty())
            return false;
        Customer c = co.get();
        c.setFirstName(m.getFirstName());
        c.setLastName(m.getLastName());
        c.setEmail(m.getEmail());

        //Check if Address already exits in DB, if not save new Address
        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(m.getAddress().getStreet(), m.getAddress().getPlz(), m.getAddress().getLocation());
        if (addressOptional.isEmpty()) {
            Address address = new Address();
            address.setStreet(m.getAddress().getStreet());
            address.setPlz(m.getAddress().getPlz());
            address.setLocation(m.getAddress().getLocation());

            address = addressRepository.save(address);
            c.setAddress(address);
        } else {
            c.setAddress(addressOptional.get());
        }

        // Validate Emailaddress and save customer
        if (customerVerification.validateEmailAddress(c.getEmail())) {
            customerRepository.save(c);
            logger.info("E-Mail validation successful for customer_id: " + c.getid());
            return true;
        } else {
            logger.info("E-Mail validation failed for customer_id: " + c.getid());
            return false;
        }
    }

    @PostMapping(path = "/api/customer/", produces = "application/json")
    public Customer createCustomer(@RequestBody MessageNewCustomer m) {
        String firstName = m.getFirstName();
        String lastName = m.getLastName();
        String email = m.getEmail();
        Address address = m.getAddress();

        // Check whether costumer already exists - create new one if not existent yet
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (customer.isPresent()) {
            logger.info("Customer already existing with email " + email);
            return customer.get();
        }

        //Check if Address already exits in DB, if not save new Address
        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(address.getStreet(), address.getPlz(), address.getLocation());
        if (addressOptional.isEmpty()) {
            address = addressRepository.save(address);
        } else {
            address = addressOptional.get();
        }

        //create new Customer
        Customer c = new Customer(firstName, lastName, email, address);
        c.setDeleted(false);

        // Validate Emailaddress and save customer
        if (customerVerification.validateEmailAddress(c.getEmail())) {
            customerRepository.save(c);
            logger.info("E-Mail validation successful for customer_id: " + c.getid());
            return c;
        } else {
            logger.info("E-Mail validation failed for customer_id: " + c.getid());
            return null;
        }
    }

}
