package Persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Order {
    public static int globalCount = 0;

    @Id
    @GeneratedValue
    private int id;
    private int configurationId;
    private int customerId;
    private int quantity;

    // CONSTRUCTOR
    public Order(int customerId, int configurationId, int quantity) {
        this.id = globalCount++;
        this.customerId = customerId;
        this.configurationId = configurationId;
        this.quantity = quantity;
    }

    public Order() {

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
