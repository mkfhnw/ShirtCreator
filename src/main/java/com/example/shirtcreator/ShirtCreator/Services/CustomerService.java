package com.example.shirtcreator.ShirtCreator.Services;

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
        c.setEmail(customer.getEmail());

        Optional<Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(c.getAddress().getStreet(), c.getAddress().getPlz(), c.getAddress().getLocation());
                if(addressOptional.isEmpty()){
                    Address address = new Address();
                    address.setStreet(c.getAddress().getStreet());
                    address.setPlz(c.getAddress().getPlz());
                    address.setLocation(c.getAddress().getLocation());

                    address = addressRepository.save(address);
                    c.setAddress(address);
                } else {
                    c.setAddress(addressOptional.get());
                }
        c.setAddress(customer.getAddress());
        customerRepository.save(c);
        return true;
    }

    @PostMapping(path = "/api/customer/", produces = "application/json")
    public Customer createCustomer(@RequestBody Customer customer) {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String email = customer.getEmail();

        Address address = customer.getAddress();
        Optional <Address> addressOptional = addressRepository.findByStreetAndPlzAndLocation(address.getStreet(), address.getPlz(), address.getLocation());
        if (addressOptional.isEmpty()) {
            address = addressRepository.save(address);
        } else {
            address = addressOptional.get();
        }
        customer.setAddress(address);

        Customer c = new Customer(firstName, lastName, email, address);
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
