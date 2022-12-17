package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_TABLE")
public class Order {
    //public static int globalCount = 0;

    @Id
    @Column(name="ORDER_ID")
    @GeneratedValue
    private int orderId;
    @Column(name="FK_CONFIGURATION_ID")
    //@ManyToOne
    private int configurationId;
    @Column(name="FK_CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_customer_id")
    private Customer customerId;
    @Column(name="QUANTITY")
    private int quantity;

    // CONSTRUCTOR
    public Order(Customer customerId, int configurationId, int quantity) {
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
        return orderId;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}
