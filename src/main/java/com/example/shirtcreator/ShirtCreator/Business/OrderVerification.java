package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderVerification {

    public static final int MAX_QUANTITY = 80; // Maximale Anzahl bestellbarer T-Shirts
    public static final int MIN_QUANTITY = 0;
    public static final int WEIGHT_TSHIRT = 350; // Gewicht eines durchschnittlichen T-Shirts in g
    public static final double MWST_RATE = 0.081;


    public boolean validateOrder(Integer totalQuantity) {
        boolean valid = false;
        if (totalQuantity >= MIN_QUANTITY) {
            if (totalQuantity <= MAX_QUANTITY) {
                valid = true;
            }
        }
        return valid;
    }


    // Berechnet den Preis einer Bestellung
    public Double calculateOrderPrice(Order o) {
        Double netPrice = 0.0;
        Double orderPrice = 0.0;

        // Nettopreis berechnen
        for (int i = 0; i < o.getItems().size(); i++) {
            Double cPrice = o.getItems().get(i).getConfiguration().getPrice();
            Integer quantity = o.getItems().get(i).getQuantity();
            netPrice += cPrice * quantity;
        }

        // Preis der Bestellung berechnen
        orderPrice += netPrice;
        orderPrice *= 1 + MWST_RATE;
        orderPrice = Math.round(orderPrice * 100.0 / 5.0) * 5.0 / 100.0;
        if (o.getTotalQuantity() > 0) {
            orderPrice += calculateShippingCosts(o);
        }

        return orderPrice;
    }

    // Berechnet aus Gewicht und Versandbedingung die Kosten für den Paketversand
    private Double calculateShippingCosts(Order o) {
        // Totale Menge berechnen
        int totalQuantity = o.getTotalQuantity();

        // Totales Gewicht berechnen
        int totalWeight = (totalQuantity * WEIGHT_TSHIRT) / 1000; // g in kg umgerechnet

        // Array für Paketpreise
        Double[][] packageCosts = {{7.0, 9.7, 20.5}, // Economy, mit < 2, 10, 30 kg
                {9.0, 10.7, 23.0}, // Priority, mit < 2, 10, 30 kg
                {18.0, 22.0, 29.0}}; // Express, mit < 2, 10, 30 kg

        Integer col = 0;
        Integer row = 0;

        // Spaltenindex bestimmen
        if (totalWeight > 0) col = 0;
        if (totalWeight >= 2) col = 1;
        if (totalWeight >= 10) col = 2;

        // Zeilenindex bestimmen
        switch (o.getShippingMethod().toString()) {
            case "Economy" -> row = 0;
            case "Priority" -> row = 1;
            case "Express" -> row = 2;
        }

        return packageCosts[row][col];
    }

}
