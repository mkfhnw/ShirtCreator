package com.example.shirtcreator.ShirtCreator.Services.Order;

import com.example.shirtcreator.ShirtCreator.Business.OrderVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderVerification orderVerification;

    final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @PostMapping(path = "/api/order/", produces = "application/json")
    public Integer createOrder(@RequestBody MessageNewOrder m) {
        Order o = new Order();
        if (m.getCustomerId() != null) {
            Optional<Customer> customer = customerRepository.findById(m.getCustomerId());
            if (customer.isPresent()) {
                o.setCustomer(customer.get());
            }
        }

        o.setOrderDate(m.getOrderDate());
        o.setShippingMethod(Order.ShippingMethod.Economy); // Default

        o = orderRepository.save(o);
        return o.getId();
    }

    @GetMapping(path = "/api/order/{orderId}", produces = "application/json")
    public MessageOrderDetails getOrder(@PathVariable Integer orderId) {
        Optional<Order> or = orderRepository.findById(orderId);
        if (or.isPresent()) {
            Order o = or.get();
            MessageOrderDetails m = new MessageOrderDetails();
            m.setOrderId(o.getId());
            if (o.getCustomer() != null) {
                m.setCustomerId(o.getCustomer().getid());
            } else {
                m.setCustomerId(0);
            }
            m.setOrderDate(o.getOrderDate());
            m.setTotalQuantity(o.getTotalQuantity());
            m.setShippingMethod(o.getShippingMethod().toString());
            m.setPrice(orderVerification.calculateOrderPrice(o));
            for (OrderItem oi : o.getItems()) {
                MessageOrderItem moi = new MessageOrderItem();
                moi.setOrderItemId(oi.getId());
                moi.setConfiguration(oi.getConfiguration());
                moi.setQuantity(oi.getQuantity());
                m.getItems().add(moi);
            }
            return m;
        } else {
            return null;
        }
    }

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

    @PutMapping(path = "/api/order/{orderId}", produces = "application/json")
    public boolean updateOrder(@PathVariable Integer orderId, @RequestBody MessageUpdateOrder requestBody) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            logger.info("Order present with ID: " + order.getId());

            // Update customer
            if (requestBody.getCustomerId() != null) {
                Optional<Customer> customer = customerRepository.findById(requestBody.getCustomerId());
                customer.ifPresent(order::setCustomer);
            }

            // Update definitive state & date
            order.setDefinitive(requestBody.getDefinitive());
            order.setOrderDate(requestBody.getOrderDate());

            // Save & return
            if (orderVerification.validateOrder(order.getTotalQuantity())) {
                orderRepository.save(order);
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    @PutMapping(path = "/api/order/{orderId}/updateShippingMethod/{shippingMethod}", produces = "application/json")
    public boolean updateShippingMethod(@PathVariable Integer orderId, @PathVariable String shippingMethod) {
        Optional<Order> or = orderRepository.findById(orderId);
        if (or.isPresent()) {
            Order o = or.get();
            o.setShippingMethod(Order.ShippingMethod.valueOf(shippingMethod));
            o.setPrice(orderVerification.calculateOrderPrice(o));
            orderRepository.save(o);
            return true;
        } else {
            return false;
        }
    }

    @PutMapping(path = "/api/order/{orderId}/addItem", produces = "application/json")
    public Integer addItemToOrder(@PathVariable Integer orderId, @RequestBody MessageAddItemToOrder m) {
        Optional<Order> or = orderRepository.findById(orderId);
        Optional<Configuration> co = configurationRepository.findById(m.getConfigurationId());
        Integer id;
        if (or.isPresent() && co.isPresent()) {
            Order o = or.get();
            Configuration c = co.get();

            // ??berpr??fen, ob Configuration bereits in Order vorhanden
            List<Integer> configIds = new ArrayList<>();
            for (OrderItem i : o.getItems()) {
                configIds.add(i.getConfiguration().getId());
            }
            if (configIds.contains(c.getId())) { // Configuration bereits vorhanden -> Menge erh??hen
                OrderItem i = new OrderItem();
                for (OrderItem ori : o.getItems()) {
                    if (ori.getConfiguration().getId() == c.getId()) {
                        i = ori;
                    }
                }
                i.setQuantity(i.getQuantity() + m.getQuantity());
                orderItemRepository.save(i);
                id = i.getId();
            } else {
                // Configuration noch nicht vorhanden -> neues OrderItem erstellen
                OrderItem oi = new OrderItem();
                oi.setQuantity(m.getQuantity());
                oi.setConfiguration(c);
                orderItemRepository.save(oi);
                o.getItems().add(oi);
                id = oi.getId();
            }

            // Gesamtmenge Bestellung neu berechnen und setzen
            int quantity = 0;
            for (OrderItem ordIt : o.getItems()) {
                quantity += ordIt.getQuantity();
            }
            if (orderVerification.validateOrder(quantity)) {
                o.setTotalQuantity(quantity);
            } else {
                return null;
            }

            o.setPrice(orderVerification.calculateOrderPrice(o));
            orderRepository.save(o);
            return id;
        } else {
            return null;
        }
    }

    @PutMapping(path = "/api/order/{orderId}/deleteItem/{itemId}", produces = "application/json")
    public Integer deleteItemFromOrder(@PathVariable int orderId, @PathVariable int itemId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(itemId);
        if (orderOptional.isPresent() && orderItemOptional.isPresent()) {

            Order order = orderOptional.get();
            OrderItem orderItem = orderItemOptional.get();
            order.getItems().remove(orderItem);

            // Gesamtmenge Bestellung neu berechnen und setzen
            int quantity = 0;
            for (OrderItem ordIt : order.getItems()) {
                quantity += ordIt.getQuantity();
            }

            // We need at least quantity 1 to pass the verification -  a little trick is required here in case the customer just removed his last item
            boolean letPass = quantity == 0;

            if (orderVerification.validateOrder(quantity) || letPass) {
                order.setTotalQuantity(quantity);
            } else {
                return null;
            }
            order.setPrice(orderVerification.calculateOrderPrice(order));
            orderItemRepository.delete(orderItem);
            orderRepository.save(order);
            return itemId;

        } else {
            return null;
        }
    }

    @GetMapping(path = "/api/order/{orderId}/getPrice", produces = "application/json")
    public Double getOrderPrice(@PathVariable Integer orderId) {
        Optional<Order> or = orderRepository.findById(orderId);
        if (or.isPresent()) {
            Order o = or.get();
            o.setPrice(orderVerification.calculateOrderPrice(o));
            return o.getPrice();
        } else {
            return null;
        }
    }

}
