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

    @GetMapping(path = "/api/configuration", produces = "application/json")
    public Configuration getConfiguration(@RequestParam String cut, String pattern, String size, String color) {

        Configuration.Cut cut_e = Configuration.Cut.valueOf(cut);
        Configuration.Pattern pattern_e = Configuration.Pattern.valueOf(pattern);
        Configuration.Size size_e = Configuration.Size.valueOf(size);
        Configuration.Color color_e = Configuration.Color.valueOf(color);

        Configuration c = configurationRepository.findConfigurationByCutAndColorAndSizeAndPattern(cut_e, color_e, size_e, pattern_e);

        return c;
    }

}


