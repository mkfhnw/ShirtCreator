package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Entity(name = "tblConfiguration")
public class Configuration {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Cut cut;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private Pattern pattern;
    private Double price;
    private boolean deleted;

    public static final double PRICE_ROUND = 15.0;
    public static final double PRICE_VNECK = 17.0;
    public static final double PRICE_POLO = 25.0;
    public static final double PRICE_PATTERN = 10.0;

    public enum Cut {
        Round("Round"),VNeck("VNeck"),Polo("Polo");

        private String asString;

        Cut( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;
        }

    }


    public enum Color {
        White("White"),Black("Black"),Red("Red"),Blue("Blue"),Green("Green");

        private String asString;

        Color( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;

        }
    }

    public enum Size {
        Small("Small"),Medium("Medium"),Large("Large");
        private String asString;

        Size( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;

        }
    }

    public enum Pattern {
        Plain("Plain"),Check("Check"),Striped("Striped");
        private String asString;

        Pattern( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;
        }
    }



    //Constructor(s)
    public Configuration(Integer id, Cut cut,Color color,Size size,Pattern pattern){
        this.id = id;
        this.cut = cut;
        this.color = color;
        this.size = size;
        this.pattern = pattern;
        this.deleted = false;
        this.price = calculateConfigurationPrice();
    }

    public Configuration( ) {

    }

    //GETTER & SETTER
    public Integer getId( ) {
        return id;
    }

    public Cut getCut( ) {
        return cut;
    }

    public void setCut( Cut cut ) {
        this.cut = cut;
    }

    public Color getColor( ) {
        return color;
    }

    public void setColor( Color color ) {
        this.color = color;
    }

    public Size getSize( ) {
        return size;
    }

    public void setSize( Size size ) {
        this.size = size;
    }

    public Pattern getPattern( ) {
        return pattern;
    }

    public void setPattern( Pattern pattern ) {
        this.pattern = pattern;
    }

    public boolean isDeleted( ) {
        return deleted;
    }

    public void setDeleted( boolean deleted ) {this.deleted = deleted;}

    public Double getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = calculateConfigurationPrice();
    }

    // Berechnet den Preis einer Konfiguration
    private Double calculateConfigurationPrice() {
        Double configurationPrice = 0.0;

        // HashMap für Schnitt-Preise (nach Cut)
        Map<Cut, Double> basePrices = new HashMap<>();
        basePrices.put(Cut.Round, PRICE_ROUND);
        basePrices.put(Cut.VNeck, PRICE_VNECK);
        basePrices.put(Cut.Polo, PRICE_POLO);

        // Preis für 1 T-Shirt berechnen
        configurationPrice += basePrices.get(this.cut);
        if (this.pattern != Pattern.Plain) {
            configurationPrice += PRICE_PATTERN;
        }

        return configurationPrice;
    }


}
