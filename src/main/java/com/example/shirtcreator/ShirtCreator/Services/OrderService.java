package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Business.OrderVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.Customer;
import com.example.shirtcreator.ShirtCreator.Persistence.CustomerRepository;
import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import com.example.shirtcreator.ShirtCreator.Persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
    public Order createOrder(@RequestBody MessageNewOrder m) {
        System.out.println("createOrder");
        Order o = new Order();

        //TODO:
        //Customer c = customerRepository.findById(m.getCustomerId());

        o.setConfigurationId(m.getConfigurationId());
        o.setQuantity(m.getQuantity());

        if(orderVerification.validateOrder(o)) {
            orderRepository.save(o);
            return o;
        } else {
            return null;
        }
    }

}
