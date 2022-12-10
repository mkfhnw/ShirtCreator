package Persistence;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepository {

    private List<Customer> customers = new ArrayList<>();

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getCustomerById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Saves customer to list
     * If a customer with the same ID already exists, it will be replaced
     */
    public void saveCustomer(Customer c) {
        Customer removeOld = null;
        for (Customer cOld : customers) {
            if (cOld.getId() == c.getId()) {
                removeOld = cOld;
                break;
            }
        }
        if (removeOld != null)
            customers.remove(removeOld);
        customers.add(c);
    }


}
