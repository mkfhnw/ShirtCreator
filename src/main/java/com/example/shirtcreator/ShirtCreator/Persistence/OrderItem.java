package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity(name = "tblOrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Configuration configuration;
    private int quantity;

    // CONSTRUCTOR
    public OrderItem() {

    }

    public Integer getId() {
        return id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
