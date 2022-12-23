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
    //TODO fix bug, method doesnt work.
    @DeleteMapping(path = "/api/configuration/{id}", produces = "application/json")
    public boolean deleteConfiguration(@RequestParam Integer id) {
        Configuration c = configurationRepository.getOne(id);
        if (c == null)
            return false;
        c.setDeleted(true);
        configurationRepository.save(c);
        return true;
    }


    @PutMapping(path = "/api/configuration/{id}", produces = "application/json")
    public boolean updateConfiguration(@PathVariable Integer id, @RequestBody Configuration configuration) {
        Configuration c = configurationRepository.getOne(id);
        if (c == null)
            return false;

        c.setSize(configuration.getSize());
        c.setColor(configuration.getColor());
        c.setCut(configuration.getCut());
        c.setPattern(configuration.getPattern());
        // Preis der Konfiguration berechnen und setzen
        c.setPrice(configurationVerification.calculateConfigurationPrice(c));

        configurationRepository.save(c);
        return true;
    }
    @PostMapping(path = "/api/configuration", produces = "application/json")
    public Configuration createConfiguration(@RequestBody MessageNewConfiguration m) {
        Configuration c = new Configuration();

        Configuration.Cut cut = Configuration.Cut.valueOf(m.getCut());
        Configuration.Size size = Configuration.Size.valueOf(m.getSize());
        Configuration.Pattern pattern = Configuration.Pattern.valueOf(m.getPattern());
        Configuration.Color color = Configuration.Color.valueOf(m.getColor());

        c.setSize(size);
        c.setColor(color);
        c.setCut(cut);
        c.setPattern(pattern);
        c.setDeleted(false);

        configurationRepository.save(c);

        return null;
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


