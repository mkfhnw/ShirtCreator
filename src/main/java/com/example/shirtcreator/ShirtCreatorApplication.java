package com.example.shirtcreator;

import com.example.shirtcreator.ShirtCreator.Persistence.Configuration;
import com.example.shirtcreator.ShirtCreator.Persistence.ConfigurationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShirtCreatorApplication {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShirtCreatorApplication.class, args);
    }

    @PostConstruct
    // Create all possible configurations
    public void createConfigurationData() {
        int i = 1;
        for (Configuration.Cut cut: Configuration.Cut.values()) {
            for (Configuration.Color color: Configuration.Color.values()) {
                for (Configuration.Size size: Configuration.Size.values()) {
                    for (Configuration.Pattern pattern: Configuration.Pattern.values()) {
                        Configuration config = new Configuration(i, cut, color, size, pattern);
                        configurationRepository.save(config);
                        i++;
                    }
                }
            }
        }


    }

}
