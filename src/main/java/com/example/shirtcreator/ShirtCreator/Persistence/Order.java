package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tblOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Configuration configuration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;
    private Double price;

    Date orderDate;

    // CONSTRUCTOR
    public Order(Customer customer, Configuration configuration, int quantity, ShippingMethod shippingMethod) {
        this.customer = customer;
        this.configuration = configuration;
        this.quantity = quantity;
        this.shippingMethod = shippingMethod;
        this.price = 0.0;
        this.orderDate = orderDate;
    }

    public Order() {
    }
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems = new ArrayList<>();

    // ENUM SHIPPING METHOD
    public enum ShippingMethod {
        Economy("Economy"), Priority("Priority"), Express("Express");

        private String asString;

        ShippingMethod(String asString) {
            this.asString = asString;
        }

        public String toString() {
            return asString;
        }
    }

    // GETTER & SETTER
    public int getId() {
        return Id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOrderDate( ) {
        return orderDate;
    }

    public List<OrderItem> getOrderItems( ) {
        return orderItems;
    }

    public void setOrderItems( List<OrderItem> orderItems ) {
        this.orderItems = orderItems;
    }
}
