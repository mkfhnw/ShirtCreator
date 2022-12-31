package com.example.shirtcreator.ShirtCreator.Services.Order;

import java.util.Date;

public class MessageUpdateOrder {


    private Integer customerId;
    private Date orderDate;
    private Boolean definitive;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getDefinitive() {
        return definitive;
    }

    public void setDefinitive(Boolean definitive) {
        this.definitive = definitive;
    }
}
