package com.example.shirtcreator.ShirtCreator.Services;

import com.example.shirtcreator.ShirtCreator.Business.ConfigurationVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;
import com.example.shirtcreator.ShirtCreator.Persistence.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private ConfigurationVerification configurationVerification;

    @GetMapping(path = "/api/configuration/{configurationId}", produces = "application/json")
    public MessageNewConfiguration getConfiguration(@PathVariable int configurationId) {
        Optional<Configuration> config = configurationRepository.findById(configurationId);
        if(config.isPresent()){
            Configuration c = config.get();

            MessageNewConfiguration m = new MessageNewConfiguration();
            m.setCut(String.valueOf(c.getCut()));
            m.setPattern(String.valueOf(c.getPattern()));
            m.setSize(String.valueOf(c.getSize()));
            m.setColor(String.valueOf(c.getColor()));

            return m;

        }else{ return null; }

    }

    @GetMapping(path = "/api/configuration/search", produces = "application/json")
    public Integer getConfigurationId(@RequestBody MessageGetConfigurationId m) {

        Configuration.Cut cut = Configuration.Cut.valueOf(m.getCut());
        Configuration.Color color = Configuration.Color.valueOf(m.getColor());
        Configuration.Size size = Configuration.Size.valueOf(m.getSize());
        Configuration.Pattern pattern = Configuration.Pattern.valueOf(m.getPattern());

        Configuration c = configurationRepository.findConfigurationByCutAndColorAndSizeAndPattern(cut, color, size, pattern);

        return c.getId();
    }

    @PostMapping(path = "/api/configuration/getPrice", produces = "application/json")
    public Double getConfigurationPrice(@RequestBody MessageNewConfiguration m) {
        Configuration.Cut cut = Configuration.Cut.valueOf(m.getCut());
        Configuration.Size size = Configuration.Size.valueOf(m.getSize());
        Configuration.Pattern pattern = Configuration.Pattern.valueOf(m.getPattern());
        Configuration.Color color = Configuration.Color.valueOf(m.getColor());

        /**
         * ID beliebig gewählt, da nicht relevant für Preisberechnung
         * Configuration muss auch nicht gesucht/abgefragt werden
         * Nur Zusammenstellung entscheidend für Preis
         */
        Configuration c = new Configuration(1, cut, color, size, pattern);

        return configurationVerification.calculateConfigurationPrice(c);
    }

    // TODO: getPrice with configuration ID
    // ID der Konfiguration wird zuerst abgefragt, anschliessend kann mit dieser ID direkt der Preis geladen werden



}


