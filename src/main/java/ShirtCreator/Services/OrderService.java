package ShirtCreator.Services;

import ShirtCreator.Business.OrderVerification;
import ShirtCreator.Persistence.Order;
import ShirtCreator.Persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderVerification orderVerification;

    @GetMapping(path = "/api/orders", produces = "application/json")
    public List<Order> getOrders(@RequestParam(required = false) int customerId) {
        System.out.println("getOrders");
        // TODO Use Filter customerId
        return orderRepository.findAllByCustomerId(customerId);
    }


    @PostMapping(path = "/api/order/", produces = "application/json")
    public Order createOrder(@RequestParam Order order) {
        Order o = new Order(order.getCustomerId(), order.getConfigurationId(), order.getQuantity());

        if(orderVerification.validateOrder(o)) {
            orderRepository.save(o);
            return o;
        } else {
            return null;
        }
    }

}
