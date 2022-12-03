package Models;

public class Order {
    public static int globalCount = 0;

    private int orderId;
    private int configurationId;
    private int customerId;
    private int quantity;

    // Constructor
    public Order(int customerId, int configurationId, int quantity) {
        globalCount++;
        this.orderId = globalCount;
        this.customerId = customerId;
        this.configurationId = configurationId;
        this.quantity = quantity;
    }


    // GETTER & SETTER
    public int getOrderId() {
        return orderId;
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
