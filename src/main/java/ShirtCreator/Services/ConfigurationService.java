package ShirtCreator.Services;

import ShirtCreator.Persistence.Configuration;
import ShirtCreator.Persistence.ConfigurationRepository;
import ShirtCreator.Persistence.TShirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;
    @GetMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public Configuration getConfiguration(@RequestParam(required = false) int configurationId ){
    return configurationRepository.getConfigurationsOfCustomer(configurationId);
    }

    @GetMapping(path = "/api/configuration/{configuration}", produces = "application/json")
    public int getConfigurationId (@RequestParam(required = false) Map<String, String> requestParams) {

        if(!requestParams.containsKey("Cut")
                || !requestParams.containsKey("Color")
                || !requestParams.containsKey("Size")
                || !requestParams.containsKey("Pattern")
        ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuration not found");
        }

        return configurationRepository.getConfigurationID(
                TShirt.Cut.valueOf(requestParams.get("Cut")),
                TShirt.Color.valueOf(requestParams.get("Color")),
                TShirt.Size.valueOf(requestParams.get("Size")),
                TShirt.Pattern.valueOf(requestParams.get("Pattern"))
        );
    }

    @DeleteMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public boolean deleteConfiguration( @PathVariable int id ){
        Configuration c = configurationRepository.getConfigurationsOfCustomer(id);
        if (c == null)
            return false;
        c.setDeleted(true);
        configurationRepository.saveConfiguration(c);
                return true;
    }

    @PutMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public boolean updateConfiguration(@PathVariable int id, @RequestBody Configuration configuration) {
        Configuration c = configurationRepository.getConfigurationsOfCustomer(id);
        if (c == null)
            return false;

        c.gettShirt().setSize(configuration.gettShirt().getSize());
        c.gettShirt().setColor(configuration.gettShirt().getColor());
        c.gettShirt().setCut(configuration.gettShirt().getCut());
        c.gettShirt().setPattern(configuration.gettShirt().getPattern());

        configurationRepository.saveConfiguration(c);
        return true;
    }

 /*   public Configuration createConfiguration(@RequestBody Configuration configuration){
        Configuration c = new Configuration();

        c.setConfigurationId(123);
        c.gettShirt().setSize(configuration.gettShirt().getSize());
        c.gettShirt().setColor(configuration.gettShirt().getColor());
        c.gettShirt().setCut(configuration.gettShirt().getCut());
        c.gettShirt().setPattern(configuration.gettShirt().getPattern());
        c.setDeleted(false);

        configurationRepository.saveConfiguration(c);

        return c;
    }*/
}
