package com.example.shirtcreator.ShirtCreator.Persistence;

public class TShirt {

    public static int globalCount = 0;
    private Cut cut;
    private Color color;
    private Size size;
    private Pattern pattern;

    public int tShirtId, artNr;

    //Constructor
    public TShirt(Cut cut, Color color, Size size, Pattern pattern){
        this.tShirtId = globalCount ++;
        this.cut = Cut.valueOf(cut.toString());
        this.color = Color.valueOf(color.toString());
        this.size = Size.valueOf(size.toString());
        this.pattern = Pattern.valueOf(pattern.toString());
    }

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

    //GETTER & SETTER

    public Cut getCut( ) {
        return cut;
    }

    public Color getColor( ) {
        return color;
    }

    public Size getSize( ) {
        return size;
    }

    public Pattern getPattern( ) {
        return pattern;
    }

    public int gettShirtId( ) {
        return tShirtId;
    }

     public int getArtNr( ) {
        return artNr;
    }

    public void setArtNr( int artNr ) {
        this.artNr = artNr;
    }

    public void setCut(Cut cut){
        this.cut = cut;

    }
    public void setSize(Size size){
        this.size = size;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setPattern(Pattern pattern){
        this.pattern = pattern;
    }
}




