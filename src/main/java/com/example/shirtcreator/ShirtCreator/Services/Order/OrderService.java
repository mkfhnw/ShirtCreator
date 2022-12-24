package com.example.shirtcreator.ShirtCreator.Services.Order;

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
    private ItemRepository itemRepository;

    @Autowired
    private OrderVerification orderVerification;

    @GetMapping(path = "/api/orders", produces = "application/json")
    public List<MessageOrderShort> getOrdersForCustomer(@RequestParam Integer customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        List<MessageOrderShort> m = new ArrayList<>();
        for (Order o : orders) {
            MessageOrderShort mos = new MessageOrderShort();
            mos.setOrderId(o.getId());
            mos.setCustomerId(o.getCustomer().getid());
            mos.setOrderDate(o.getOrderDate());
            mos.setTotalQuantity(o.getTotalQuantity());
            mos.setShippingMethod(o.getShippingMethod().toString());
            mos.setPrice(o.getPrice());
            m.add(mos);
        }
        return m;
    }

    @PostMapping(path = "/api/order/", produces = "application/json")
    public Integer createOrder(@RequestBody MessageNewOrder m) {
        Order o = new Order();
        Optional<Customer> customer = customerRepository.findById(m.getCustomerId());

        if (customer.isPresent()) {
            o.setCustomer(customer.get());
        }
        o.setOrderDate(m.getOrderDate());

        o = orderRepository.save(o);
        return o.getId();
    }

    @GetMapping(path = "/api/order/{id}", produces = "application/json")
    public MessageOrderDetails getOrder(@PathVariable Integer orderId) {
        Optional<Order> or = orderRepository.findById(orderId);
        if (or.isPresent()) {
            Order o = or.get();
            MessageOrderDetails m = new MessageOrderDetails();
            m.setOrderId(o.getId());
            m.setCustomerId(o.getCustomer().getid());
            m.setOrderDate(o.getOrderDate());
            m.setTotalQuantity(o.getTotalQuantity());
            m.setShippingMethod(o.getShippingMethod().toString());
            m.setPrice(orderVerification.calculateOrderPrice(o));
            for (OrderItem oi : o.getItems()) {
                MessageOrderItem moi = new MessageOrderItem();
                moi.setQuantity(oi.getQuantity());
                moi.setConfigurationId(oi.getConfiguration().getId());
                m.getItems().add(moi);
            }
            return m;
        } else {
            return null;
        }
    }

    @PutMapping(path = "/api/order/{id}/addItem", produces = "application/json")
    public boolean addItemToOrder(@PathVariable int orderId, @RequestBody MessageAddItemToOrder m) {
        Optional<Order> or = orderRepository.findById(orderId);
        if (or.isPresent()) {
            OrderItem oi = new OrderItem();
            oi.setQuantity(m.getQuantity());
            oi.setConfiguration(configurationRepository.getOne(m.getConfigurationId()));
            Order o = or.get();
            o.getItems().add(oi);
            o.setPrice(orderVerification.calculateOrderPrice(o));
            itemRepository.save(oi);
            orderRepository.save(o);
            return true;
        } else {
            return false;
        }
    }

    @PutMapping(path = "/api/order/{orderId}/deleteItem/{itemId}", produces = "application/json")
    public boolean deleteItemFromOrder(@PathVariable int orderId, @PathVariable int itemId) {
        Optional<Order> or = orderRepository.findById(orderId);
        Optional<OrderItem> oi = itemRepository.findById(itemId);
        if (or.isPresent() && oi.isPresent()) {
            Order o = or.get();
            o.getItems().remove(oi.get());
            o.setPrice(orderVerification.calculateOrderPrice(o));
            orderRepository.save(o);
            return true;
        } else {
            return false;
        }
    }

//    @GetMapping(path = "/api/order/getPrice", produces = "application/json")
//    public Double getOrderPrice(@RequestBody MessageOrder m) {
//        Order o = new Order();
//        Optional<Configuration> c = configurationRepository.findById(m.getConfigurationId());
//        if (c.isPresent()) {
//            o.setConfiguration(c.get());
//        }
//        o.setShippingMethod(Order.ShippingMethod.valueOf(m.getShippingMethod()));
//        o.setTotalQuantity(m.getTotalQuantity());
//        if (orderVerification.validateOrder(o)) {
//            // Preis der Bestellung berechnen und zurückgeben
//            return orderVerification.calculateOrderPrice(o);
//        } else {
//            return null;
//        }
//    }

}
