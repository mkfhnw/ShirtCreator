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
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;
    private Double price;

    public static final int WEIGHT_TSHIRT = 350; // Gewicht eines durchschnittlichen T-Shirts in g
    public static final double MWST_RATE = 0.081;

    // CONSTRUCTOR
    public Order(Customer customer, Configuration configuration, int quantity, ShippingMethod shippingMethod) {
        this.customer = customer;
        this.configuration = configuration;
        this.quantity = quantity;
        this.shippingMethod = shippingMethod;
        this.price = calculateOrderPrice();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = calculateOrderPrice();
    }

    // Berechnet den Preis einer Bestellung
    private Double calculateOrderPrice() {
        Double configurationPrice = this.configuration.getPrice();
        Double orderPrice = 0.0;

        // Preis der Bestellung berechnen
        orderPrice += configurationPrice * this.quantity;
        orderPrice *= 1 + MWST_RATE;
        orderPrice = Math.round(orderPrice * 100.0 / 5.0) * 5.0 / 100.0;
        orderPrice += calculateShippingCosts();

        return orderPrice;
    }

    // Berechnet aus Gewicht und Versandbedingung die Kosten für den Paketversand
    private Double calculateShippingCosts() {
        int totalWeight = (this.quantity * WEIGHT_TSHIRT) / 1000; // g in kg umgerechnet

        // Array für Paketpreise
        Double[][] packageCosts = {{7.0, 9.7, 20.5}, // Economy, mit < 2, 10, 30 kg
                {9.0, 10.7, 23.0}, // Priority, mit < 2, 10, 30 kg
                {18.0, 22.0, 29.0}}; // Express, mit < 2, 10, 30 kg

        // Defaultwerte = Economy < 2 kg
        int col = 0;
        int row = 0;

        // Spaltenindex bestimmen
        if (totalWeight >= 2) col = 1;
        if (totalWeight >= 10) col = 2;

        // Zeilenindex bestimmen
        switch (this.shippingMethod.toString()) {
            case "Economy":
                row = 0;
                break;
            case "Priority":
                row = 1;
                break;
            case "Express":
                row = 2;
                break;
        }

        return packageCosts[row][col];
    }

}
