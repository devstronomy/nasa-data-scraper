package com.devstronomy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    private String nasaPlanetaryDataSheet;
    private String pathLocalPython;

    public String getPathLocalPython() {
        return pathLocalPython;
    }

    public void setName(String pathLocalPython) {
        this.pathLocalPython = pathLocalPython;
    }

    public void setPathLocalPython(String pathLocalPython) {
        this.pathLocalPython = pathLocalPython;
    }

    public String getNasaPlanetaryDataSheet() {
        return nasaPlanetaryDataSheet;
    }

    public void setNasaPlanetaryDataSheet(String nasaPlanetaryDataSheet) {
        this.nasaPlanetaryDataSheet = nasaPlanetaryDataSheet;
    }

}