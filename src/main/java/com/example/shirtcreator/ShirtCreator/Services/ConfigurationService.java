package com.example.shirtcreator.ShirtCreator.Services;

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

    @GetMapping(path = "/api/configuration/{id}", produces = "application/json")
    public Configuration getConfiguration(@PathVariable Integer id) {
        /**
        Optional<Configuration> o = configurationRepository.findById(id);
        if (o.isPresent()) {
            return o;
        } else {
            return null;
        }*/
    return null;
    }

    @GetMapping(path = "/api/configuration/search", produces = "application/json")
    public ConfigurationRepository getConfigurationId ( @RequestParam(required = false) Map<String, String> requestParams) {

        if(!requestParams.containsKey("Cut")
                || !requestParams.containsKey("Color")
                || !requestParams.containsKey("Size")
                || !requestParams.containsKey("Pattern")
        ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuration not found");
        }

        return configurationRepository;

        /** TODO: fixBug --> return configurationRepository.??
                TShirt.Cut.valueOf(requestParams.get("Cut")),
                TShirt.Color.valueOf(requestParams.get("Color")),
                TShirt.Size.valueOf(requestParams.get("Size")),
                TShirt.Pattern.valueOf(requestParams.get("Pattern"));
        */
    }

    @DeleteMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public boolean deleteConfiguration( @RequestParam Integer id ){
        Configuration c = configurationRepository.getOne(id);
        if (c == null)
            return false;
        c.setDeleted(true);
        configurationRepository.save(c);
                return true;
    }

    @PutMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public boolean updateConfiguration(@PathVariable Integer id, @RequestBody Configuration configuration) {
        Configuration c = configurationRepository.getOne(id);
        if (c == null)
            return false;

        c.setSize(configuration.getSize());
        c.setColor(configuration.getColor());
        c.setCut(configuration.getCut());
        c.setPattern(configuration.getPattern());

        configurationRepository.save(c);
        return true;
    }
    @PostMapping(path = "/api/configuration/", produces = "application/json")
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


}


