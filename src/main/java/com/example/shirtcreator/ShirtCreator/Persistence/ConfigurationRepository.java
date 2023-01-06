package com.example.shirtcreator.ShirtCreator.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository <Configuration, Integer> {
    Configuration findConfigurationByCutAndColorAndSizeAndPattern(Configuration.Cut cut, Configuration.Color color, Configuration.Size size, Configuration.Pattern pattern);
}
