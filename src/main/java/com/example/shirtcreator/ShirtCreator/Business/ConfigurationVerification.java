package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationVerification {

    public static final double PRICE_ROUND = 15.0;
    public static final double PRICE_VNECK = 17.0;
    public static final double PRICE_POLO = 25.0;
    public static final double PRICE_PATTERN = 10.0;

    // Berechnet den Preis einer Konfiguration
    public Double calculateConfigurationPrice(Configuration c) {
        Double configurationPrice = 0.0;

        // HashMap für Schnitt-Preise (nach Cut)
        Map<Configuration.Cut, Double> basePrices = new HashMap<>();
        basePrices.put(Configuration.Cut.Round, PRICE_ROUND);
        basePrices.put(Configuration.Cut.VNeck, PRICE_VNECK);
        basePrices.put(Configuration.Cut.Polo, PRICE_POLO);

        // Preis für 1 T-Shirt berechnen
        configurationPrice += basePrices.get(c.getCut());
        if (c.getPattern() != Configuration.Pattern.Plain) {
            configurationPrice += PRICE_PATTERN;
        }

        return configurationPrice;
    }

}
