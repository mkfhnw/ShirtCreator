package com.example.shirtcreator.ShirtCreator.Services.Customer;

import com.example.shirtcreator.ShirtCreator.Business.CustomerVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.Address;
import com.example.shirtcreator.ShirtCreator.Persistence.AddressRepository;
import com.example.shirtcreator.ShirtCreator.Persistence.Customer;
import com.example.shirtcreator.ShirtCreator.Persistence.CustomerRepository;
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
        Customer c = customerRepository.getOne(id);
        if (c == null)
            return false;
        c.setDeleted(true);
        customerRepository.save(c);
        return true;
    }

    @PutMapping(path = "/api/customer/{id}", produces = "application/json")
    public boolean updateCustomer(@PathVariable Integer id, @RequestBody MessageNewCustomer m) {
        Customer c = customerRepository.getOne(id);
        if (c == null)
            return false;
        c.setFirstName(m.getFirstName());
        c.setLastName(m.getLastName());
        c.setEmail(m.getEmail());

        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(c.getAddress().getStreet(), c.getAddress().getPlz(), c.getAddress().getLocation());
        if (addressOptional.isEmpty()) {
            Address address = new Address();
            address.setStreet(c.getAddress().getStreet());
            address.setPlz(c.getAddress().getPlz());
            address.setLocation(c.getAddress().getLocation());

            address = addressRepository.save(address);
            c.setAddress(address);
        } else {
            c.setAddress(addressOptional.get());
        }
        c.setAddress(m.getAddress());

        if (customerVerification.validateEmailAddress(c.getEmail())) {
            customerRepository.save(c);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/api/customer/", produces = "application/json")
    public Customer createCustomer(@RequestBody MessageNewCustomer m) {
        String firstName = m.getFirstName();
        String lastName = m.getLastName();
        String email = m.getEmail();

        Address address = m.getAddress();
        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(address.getStreet(), address.getPlz(), address.getLocation());
        if (addressOptional.isEmpty()) {
            address = addressRepository.save(address);
        } else {
            address = addressOptional.get();
        }
        m.setAddress(address);

        Customer c = new Customer(firstName, lastName, email, address);
        c.setDeleted(false);

        if (customerVerification.validateEmailAddress(c.getEmail())) {
            customerRepository.save(c);
            return c;
        } else {
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
