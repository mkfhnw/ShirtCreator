package com.example.shirtcreator.ShirtCreator.Services.Configuration;

import com.example.shirtcreator.ShirtCreator.Business.ConfigurationVerification;
import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;
import com.example.shirtcreator.ShirtCreator.Persistence.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @GetMapping(path = "/api/configuration/", produces = "application/json")
    public Configuration getConfiguration(@RequestBody MessageConfiguration m) {

        Configuration.Cut cut = Configuration.Cut.valueOf(m.getCut());
        Configuration.Color color = Configuration.Color.valueOf(m.getColor());
        Configuration.Size size = Configuration.Size.valueOf(m.getSize());
        Configuration.Pattern pattern = Configuration.Pattern.valueOf(m.getPattern());

        Configuration c = configurationRepository.findConfigurationByCutAndColorAndSizeAndPattern(cut, color, size, pattern);

        return c;
    }

}


