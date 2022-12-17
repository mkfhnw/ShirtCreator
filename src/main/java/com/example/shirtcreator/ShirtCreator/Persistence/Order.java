package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;

import java.util.Optional;

@Entity(name = "tblOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    //@ManyToOne
    private int configurationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    private int quantity;

    // CONSTRUCTOR
    public Order(Customer customer, int configurationId, int quantity) {
        this.customer = customer;
        this.configurationId = configurationId;
        this.quantity = quantity;
    }

    public Order() {
    }


    // GETTER & SETTER
    public int getId() {
        return Id;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
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
}
