package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Persistence.Order;

public class MessageNewOrder {
    private Integer customerId;
    private Integer configurationId;
    private Integer quantity;

    private String shippingMethod;

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Integer configurationId) {
        this.configurationId = configurationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
