package com.example.shirtcreator.ShirtCreator.Services.Order;

public class MessageAddItemToOrder {
    private Integer quantity, configurationId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(Integer configurationId) {
        this.configurationId = configurationId;
    }
}
