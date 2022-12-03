package Services;

import Models.Order;
import Models.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // TODO is this getOrders needed?
    @GetMapping(path = "/api/orders", produces = "application/json")
    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }


    @PostMapping(path = "/api/order/", produces = "application/json")
    public Order createOrder(@RequestParam Order order) {
        Order o = new Order(order.getCustomerId(), order.getConfigurationId(), order.getQuantity());
        orderRepository.addOrder(o);

        return o;
    }

}
