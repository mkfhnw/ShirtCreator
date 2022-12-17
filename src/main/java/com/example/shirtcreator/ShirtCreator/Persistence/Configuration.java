package com.example.shirtcreator.ShirtCreator.Persistence;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity(name = "tblConfiguration")
public class Configuration {
    @Id
    @GeneratedValue
    private Integer id;

    private Cut cut;
    private Color color;
    private Size size;
    private Pattern pattern;
    private boolean deleted;

    public enum Cut {
        Round("Rund"),VNeck("VNeck"),Polo("Polo");

        private String asString;

        Cut( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;
        }

    }


    public enum Color {
        White("weiss"),Black("schwarz"),Red("rot"),Blue("blau"),Green("grün");

        private String asString;

        Color( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;

        }
    }

    public enum Size {
        Small("S"),Medium("M"),Large("L");
        private String asString;

        Size( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;

        }
    }

    public enum Pattern {
        Plain("einfarbig"),Check("kariert"),Striped("gestreift");
        private String asString;

        Pattern( String asString ) {
            this.asString = asString;
        }

        public String toString( ) {
            return asString;
        }
    }



    //Constructor(s)
    public Configuration( Integer id,Cut cut,Color color,Size size,Pattern pattern){
        this.id = id;
        this.cut = Cut.valueOf(cut.toString());
        this.color = Color.valueOf(color.toString());
        this.size = Size.valueOf(size.toString());
        this.pattern = Pattern.valueOf(pattern.toString());
    }

    public Configuration( ) {

    }

    //GETTER & SETTER
    public Integer getConfigurationId( ) {
        return id;
    }

    public void setConfigurationId( int configurationId ) {this.id = configurationId;}

    public void setConfigurationId( Integer configurationId ) {
        this.id = configurationId;
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
}
