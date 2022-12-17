package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Persistence.Customer;
import com.example.shirtcreator.ShirtCreator.Persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/api/customer/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable int id) {
        return customerRepository.getOne(id);
    }

    @DeleteMapping(path = "/api/customer/{id}", produces = "application/json")
    public boolean deleteCustomer(@PathVariable int id) {
        Customer c = customerRepository.getOne(id);
        if (c == null)
            return false;
        c.setDeleted(true);
        customerRepository.save(c);
        return true;
    }

    @PutMapping(path = "/api/customer/{id}", produces = "application/json")
    public boolean updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        Customer c = customerRepository.getOne(id);
        if (c == null)
            return false;
        c.setFirstName(customer.getFirstName());
        c.setLastName(customer.getLastName());
        c.setStreet(customer.getStreet());
        c.setPlz(customer.getPlz());
        c.setLocation(customer.getLocation());
        c.setEmail(customer.getEmail());
        customerRepository.save(c);
        return true;
    }

    @PostMapping(path = "/api/customer/", produces = "application/json")
    public Customer createCustomer(@RequestBody Customer customer) {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String street = customer.getStreet();
        int plz = customer.getPlz();
        String location = customer.getLocation();
        String email = customer.getEmail();
        Customer c = new Customer(firstName, lastName, street, plz, location, email);
        c.setDeleted(false);
        customerRepository.save(c);
        return c;
    }

    @GetMapping(path = "/api/customers", produces = "application/json")
    public List<Customer> getCustomers(@RequestParam(required = false) String email,
                                       @RequestParam(required = false) String firstName,
                                       @RequestParam(required = false) String lastName,
                                       @RequestParam(required = false) String street,
                                       @RequestParam(required = false) int plz,
                                       @RequestParam(required = false) String location) {
        return customerRepository.findAll();
    }

}
