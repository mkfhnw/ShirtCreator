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
    Logger logger = LoggerFactory.getLogger(CustomerService.class);


    @GetMapping(path = "/api/customer/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable Integer id) {
        Optional<Customer> c = customerRepository.findById(id);
        if (c.isPresent()) {
            return c.get();
        } else {
            return null;
        }
    }

    @DeleteMapping(path = "/api/customer/{id}", produces = "application/json")
    public boolean deleteCustomer(@PathVariable Integer id) {
        Optional<Customer> co = customerRepository.findById(id);
        if (co.isEmpty())
            return false;
        Customer c = co.get();
        c.setDeleted(true);
        customerRepository.save(c);
        return true;
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

        //Check if Address already exits in DB, if not save new Address
        Address address = m.getAddress();
        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(address.getStreet(), address.getPlz(), address.getLocation());
        if (addressOptional.isEmpty()) {
            address = addressRepository.save(address);
        } else {
            address = addressOptional.get();
        }
        m.setAddress(address);

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

    @GetMapping(path = "/api/customers", produces = "application/json")
    public List<Customer> getCustomers(@RequestParam(required = false) String email,
                                       @RequestParam(required = false) String firstName,
                                       @RequestParam(required = false) String lastName,
                                       @RequestParam(required = false) String street,
                                       @RequestParam(required = false) Integer plz,
                                       @RequestParam(required = false) String location) {
        return customerRepository.findAll();
    }

    @GetMapping(path = "/api/customer/validateEmail", produces = "application/json")
    public boolean validateEmail(@RequestBody String email) {
        return customerVerification.validateEmailAddress(email);
    }

}
