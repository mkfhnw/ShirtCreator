package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;
import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OrderVerification {

    private final int MAX_QUANTITY = 25;
    private final int WEIGHT_TSHIRT = 350; // Gewicht durchschnittliches T-Shirt in g
    private final double PRICE_ROUND = 15.0;
    private final double PRICE_VNECK = 17.0;
    private final double PRICE_POLO = 25.0;
    private final double PRICE_PATTERN = 10.0;
    private final double MWST_RATE = 0.081;

    public boolean validateOrder(Order o) {
        if (o.getQuantity() > MAX_QUANTITY) {
            return false;
        }
        return true;
    }

    public double calculatePrice(Order o) {
        double price = 0.0;

        // HashMap für Schnitt-Preise (nach Cut)
        Map<Configuration.Cut, Double> basePrices = new HashMap<>();
        basePrices.put(Configuration.Cut.Round, PRICE_ROUND);
        basePrices.put(Configuration.Cut.VNeck, PRICE_VNECK);
        basePrices.put(Configuration.Cut.Polo, PRICE_POLO);

        // Preisberechnung
//      TODO: Cut cut = o.getConfiguration().getCut();
//          price += basePrices.get(cut);
//      TODO:  if(o.getConfiguration().getPattern() != Pattern.Plain) {
//            price += PRICE_PATTERN;
//        }
        price += price * (1 + MWST_RATE);
        price += calculateShippingCost(o);

        return price;
    }

    // Berechnet aus Gewicht und Versandbedingung die Kosten für den Paketversand
    private double calculateShippingCost(Order o) {
        int totalWeight = o.getQuantity() * WEIGHT_TSHIRT;
        String shippingMethod = o.getShippingMethod().toString();

        // Array für Paketpreise
        double[][] packageCosts = {{7.0, 9.7, 20.5}, // Economy, mit < 2, 10, 30 kg
                {9.0, 10.7, 23.0}, // Priority, mit < 2, 10, 30 kg
                {18.0, 22.0, 29.0}}; // Express, mit < 2, 10, 30 kg

        // Defaultwerte = Economy < 2 kg
        int col = 0, row = 0;

        // Spaltenindex bestimmen
        if(totalWeight >= 2) col = 1;
        if(totalWeight >= 10) col = 2;

        // Zeilenindex bestimmen
        switch(shippingMethod) {
            case "Priority" : row = 1; break;
            case "Express" : row = 2; break;
            default : row = 0;
        }

        return packageCosts[row][col];
    }

}
