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

    @OneToMany
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private int totalQuantity = 0;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    private Double price;

    private Date orderDate;

    // CONSTRUCTOR
//    public Order(Customer customer, List<OrderItem> items, ShippingMethod shippingMethod, Date orderDate) {
//        this.customer = customer;
//        this.items = items;
//        for (OrderItem i : this.items) {
//            this.totalQuantity += i.getQuantity();
//        }
//        this.shippingMethod = shippingMethod;
//        this.price = 0.0;
//        this.orderDate = orderDate;
//    }

    public Order() {
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity() {
        for (OrderItem i : this.items) {
            this.totalQuantity += i.getQuantity();
        }
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

    public void setId(int id) {
        Id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
