package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Configuration.*;
import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OrderVerification {

    public static final int MAX_QUANTITY = 80; // Maximale Anzahl bestellbarer T-Shirts
    public static final int WEIGHT_TSHIRT = 350; // Gewicht eines durchschnittlichen T-Shirts in g
    public static final double PRICE_ROUND = 15.0;
    public static final double PRICE_VNECK = 17.0;
    public static final double PRICE_POLO = 25.0;
    public static final double PRICE_PATTERN = 10.0;
    public static final double MWST_RATE = 0.081;

    public boolean validateOrder(Order o) {
        if (o.getQuantity() > MAX_QUANTITY) {
            return false;
        }
        return true;
    }

    public double calculatePrice(Order o) {
        double shirtPrice = 0.0;
        double orderPrice = 0.0;

        // HashMap für Schnitt-Preise (nach Cut)
        Map<Cut, Double> basePrices = new HashMap<>();
        basePrices.put(Cut.Round, PRICE_ROUND);
        basePrices.put(Cut.VNeck, PRICE_VNECK);
        basePrices.put(Cut.Polo, PRICE_POLO);

        // Preis für 1 T-Shirt berechnen
        Cut cut = o.getConfiguration().getCut();
        shirtPrice += basePrices.get(cut);
        if (o.getConfiguration().getPattern() != Pattern.Plain) {
            shirtPrice += PRICE_PATTERN;
        }

        // Preis der Bestellung berechnen
        orderPrice += shirtPrice * o.getQuantity();
        orderPrice *= 1 + MWST_RATE;
        orderPrice += calculateShippingCosts(o);

        return orderPrice;
    }

    // Berechnet aus Gewicht und Versandbedingung die Kosten für den Paketversand
    private double calculateShippingCosts(Order o) {
        int totalWeight = (o.getQuantity() * WEIGHT_TSHIRT) / 1000; // g in kg umgerechnet
        String shippingMethod = o.getShippingMethod().toString();

        // Array für Paketpreise
        double[][] packageCosts = {{7.0, 9.7, 20.5}, // Economy, mit < 2, 10, 30 kg
                {9.0, 10.7, 23.0}, // Priority, mit < 2, 10, 30 kg
                {18.0, 22.0, 29.0}}; // Express, mit < 2, 10, 30 kg

        // Defaultwerte = Economy < 2 kg
        int col = 0;
        int row = 0;

        // Spaltenindex bestimmen
        if (totalWeight >= 2) col = 1;
        if (totalWeight >= 10) col = 2;

        // Zeilenindex bestimmen
        switch (shippingMethod) {
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
