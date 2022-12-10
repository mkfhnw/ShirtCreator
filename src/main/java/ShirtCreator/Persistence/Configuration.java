package ShirtCreator.Persistence;

public class Configuration {

    private int configurationId;

    private TShirt tShirt;

    //Constructor
    public Configuration(int configurationId, TShirt tShirt){
        this.configurationId = configurationId;
        this.tShirt = tShirt;
    }

    //GETTER & SETTER
    public int getConfigurationId( ) {
        return configurationId;
    }

    public TShirt gettShirt() {
        return this.tShirt;
    }

    public void setConfigurationId( int configurationId ) {
        //need to be defined
        this.configurationId = configurationId;
    }

    public void setDeleted( boolean b ) {
    }
}
