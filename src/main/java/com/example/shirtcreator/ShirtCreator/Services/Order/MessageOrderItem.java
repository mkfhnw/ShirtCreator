package com.example.shirtcreator.ShirtCreator.Services.Order;

import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;

public class MessageOrderItem {
    private Configuration configuration;
    private Integer orderItemId, quantity;

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

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
}
