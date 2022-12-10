package ShirtCreator.Services;

import ShirtCreator.Business.OrderVerification;
import ShirtCreator.Persistence.Order;
import ShirtCreator.Persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderVerification orderVerification;

    @GetMapping(path = "/api/orders", produces = "application/json")
    public List<Order> getOrders(@RequestParam(required = false) Integer customerId) {
        System.out.println("getOrders");
        if(customerId != null) {
            return orderRepository.findAllByCustomerId(customerId);
        } else {
            return orderRepository.findAll();
        }
    }


    @PostMapping(path = "/api/order/", produces = "application/json")
    public Order createOrder(@RequestBody Order order) {
        System.out.println("createOrder");
        Order o = new Order();

        o.setCustomerId(order.getCustomerId());
        o.setConfigurationId(order.getConfigurationId());
        o.setQuantity(order.getQuantity());

        if(orderVerification.validateOrder(o)) {
            orderRepository.save(o);
            return o;
        } else {
            return null;
        }
    }

}
