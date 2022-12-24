package com.example.shirtcreator.ShirtCreator.Services.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageOrderDetails {
    private Integer orderId, customerId, totalQuantity;
    private Date orderDate;
    private String shippingMethod;
    private Double price;
    private List<MessageOrderItem> items = new ArrayList<>();

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<MessageOrderItem> getItems() {
        return items;
    }

    public void setItems(List<MessageOrderItem> items) {
        this.items = items;
    }
}
