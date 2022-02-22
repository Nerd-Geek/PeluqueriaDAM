package ies.luisvives.peluqueriadamtpv.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private static ApplicationProperties applicationPropertiesInstance;
    private final Properties properties;

    private ApplicationProperties() {
        properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ApplicationProperties getInstance() {
        if (applicationPropertiesInstance == null) {
            applicationPropertiesInstance = new ApplicationProperties();
        }
        return applicationPropertiesInstance;
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "Error - not assigned key");
    }
}