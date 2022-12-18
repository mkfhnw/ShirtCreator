package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Business.OrderVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.*;
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
    private ConfigurationRepository configurationRepository;

    @Autowired
    private OrderVerification orderVerification;

    @GetMapping(path = "/api/orders", produces = "application/json")
    public List<Order> getOrders(@RequestParam(required = false) Integer customerId) {
        if(customerId != null) {
            return orderRepository.findAllByCustomerId(customerId);
        } else {
            return orderRepository.findAll();
        }
    }


    @PostMapping(path = "/api/order/", produces = "application/json")
    public Order createOrder(@RequestBody MessageNewOrder m) {
        Order o = new Order();

        Optional<Customer> customer = customerRepository.findById(m.getCustomerId());
        Optional<Configuration> config = configurationRepository.findById(m.getConfigurationId());

    if (customer.isPresent() && config.isPresent()) {
        o.setCustomer(customer.get());
        o.setConfiguration(config.get());
    }
        Order.ShippingMethod sm = Order.ShippingMethod.valueOf(m.getShippingMethod());
        o.setShippingMethod(sm);
        o.setQuantity(m.getQuantity());

        if(orderVerification.validateOrder(o)) {
            orderRepository.save(o);
            return o;
        } else {
            return null;
        }
    }

}
