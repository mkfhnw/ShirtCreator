package Persistence;

import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class ConfigurationRepository {

    private ArrayList<Configuration> configurations = new ArrayList<>();

    public ArrayList<Configuration> getConfigurations(){return configurations;}

    public Configuration getConfigurationsOfCustomer(int configurationId){
        for(Configuration c : configurations){
            if(c.getConfigurationId() == configurationId){
                return c;
            }
        }
        return null;
    }

    public void addConfiguration(Configuration c) {configurations.add(c);}


    public int getConfigurationID( TShirt.Cut cut, TShirt.Color color,TShirt.Size size,TShirt.Pattern pattern ) {

        for(Configuration config : configurations) {

            TShirt tShirt = config.gettShirt();
            if(tShirt.getCut() == cut
            && tShirt.getColor() == color
            && tShirt.getSize() == size
            && tShirt.getPattern() == pattern) {
                return config.getConfigurationId();
            }
        }

        return -1;
    }
}

