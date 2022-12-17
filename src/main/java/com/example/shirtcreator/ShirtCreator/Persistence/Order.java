package com.example.shirtcreator.ShirtCreator.Persistence;


import jakarta.persistence.*;



@Entity(name = "tblOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Configuration configuration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    private int quantity;
    private ShippingMethod shippingMethod;

    // CONSTRUCTOR
    public Order(Customer customer, Configuration configuration, int quantity, ShippingMethod shippingMethod) {
        this.customer = customer;
        this.configuration = configuration;
        this.quantity = quantity;
        this.shippingMethod = shippingMethod;
    }

    public Order() {
    }

    // ENUM SHIPPING METHOD
    public enum ShippingMethod {
        Economy("Economy"), Priority("Priority"), Express("Express");

        private String asString;

        ShippingMethod(String asString) {
            this.asString = asString;
        }

        public String toString() {
            return asString;
        }
    }

    // GETTER & SETTER
    public int getId() {
        return Id;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
