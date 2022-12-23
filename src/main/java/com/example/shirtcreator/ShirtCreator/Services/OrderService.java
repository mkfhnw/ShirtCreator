package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Business.OrderVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<MessageOrder> getOrdersForCustomer(@RequestParam Integer customerId) {

        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        List<MessageOrder> m = new ArrayList<>();
        for (Order o : orders) {
            MessageOrder mo = new MessageOrder();
            mo.setId(o.getId());
            mo.setCustomerId(o.getCustomer().getid());
            mo.setConfigurationId(o.getConfiguration().getId());
            mo.setQuantity(o.getQuantity());
            mo.setShippingMethod(o.getShippingMethod().toString());
            mo.setPrice(o.getPrice());
            m.add(mo);
        }
        return m;
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

        if (orderVerification.validateOrder(o)) {
            // Preis der Bestellung berechnen und setzen
            //o.setPrice(orderVerification.calculateOrderPrice(o));
            o.setPrice(4.6);
            orderRepository.save(o);
            return o;
        } else {
            return null;
        }
    }

    @GetMapping(path = "/api/order/getPrice", produces = "application/json")
    public Double getOrderPrice(@RequestBody MessageNewOrder m) {
        Order o = new Order();
        Optional<Configuration> c = configurationRepository.findById(m.getConfigurationId());
        if (c.isPresent()) {
            o.setConfiguration(c.get());
        }
        o.setShippingMethod(Order.ShippingMethod.valueOf(m.getShippingMethod()));
        o.setQuantity(m.getQuantity());
        if (orderVerification.validateOrder(o)) {
            // Preis der Bestellung berechnen und zurückgeben
            return orderVerification.calculateOrderPrice(o);
        } else {
            return null;
        }
    }

}
