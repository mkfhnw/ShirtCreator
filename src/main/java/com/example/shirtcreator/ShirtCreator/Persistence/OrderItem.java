package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity(name = "tblOrderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "configuration")
    private Configuration configuration;
    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", configuration=" + configuration +
                ", quantity=" + quantity +
                '}';
    }
}
