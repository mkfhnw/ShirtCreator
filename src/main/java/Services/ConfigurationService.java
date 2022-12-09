package Services;

import Models.Configuration;
import Models.ConfigurationRepository;
import Models.TShirt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;
    @GetMapping(path = "/api/configuration/{ID}", produces = "application/json")
    public Configuration getConfiguration( @RequestParam(required = false) int configurationId ){
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

}
