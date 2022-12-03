package Models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository {

    private ArrayList<Order> orders = new ArrayList<>();


    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Order> getOrdersOfCustomer(int customerId) {
        ArrayList<Order> customerOrders = new ArrayList<>();
        for (Order o : orders) {
            if (o.getCustomerId() == customerId) {
                customerOrders.add(o);
            }
        }
        return customerOrders;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

}
