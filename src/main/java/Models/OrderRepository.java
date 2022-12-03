package Models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository {

    private ArrayList<Order> orders = new ArrayList<>();

// TODO is this getOrders needed?
    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

}
