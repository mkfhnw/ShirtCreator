package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;

@Entity
public class OrderItem {
@Id
@GeneratedValue
private int orderItemId;
private int quantity;

@ManyToOne
private Configuration configuration;

    public int getOrderItemId( ) {
        return orderItemId;
    }

    public void setOrderItemId( int orderItemId ) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity( ) {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public Configuration getConfiguration( ) {
        return configuration;
    }

    public void setConfiguration( Configuration configuration ) {
        this.configuration = configuration;
    }
}
