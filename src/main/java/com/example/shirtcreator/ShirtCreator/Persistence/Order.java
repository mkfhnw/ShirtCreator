package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    //public static int globalCount = 0;

    @Id
    @Column(name="ID")
    @GeneratedValue
    private int id;
    @Column(name="CONFIGURATION_ID")
    private int configurationId;
    @Column(name="CUSTOMER_ID")
    private int customerId;
    @Column(name="QUANTITY")
    private int quantity;

    // CONSTRUCTOR
    public Order(int customerId, int configurationId, int quantity) {
        //this.id = globalCount++;
        this.customerId = customerId;
        this.configurationId = configurationId;
        this.quantity = quantity;
    }

    public Order() {
        //this.id = globalCount++;
    }


    // GETTER & SETTER
    public int getId() {
        return id;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
