package com.apiframework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = loadProperties();

    private ConfigManager() {
        // utility class: no objects needed
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found in src/test/resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
        return properties;
    }

    /** A setting from config.properties. A -Dkey=value on the command line overrides it. */
    public static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override;
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        return value.trim();
    }

    /** A secret from an environment variable. Never stored in the repo. */
    public static String secret(String variableName) {
        String value = System.getenv(variableName);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Environment variable " + variableName + " is not set");
        }
        return value;
    }

    // ---- shortcuts, so tests read cleanly ----
    public static String goRestBaseUrl()   { return get("gorest.baseUrl"); }
    public static String bookerBaseUrl()   { return get("booker.baseUrl"); }
    public static String petstoreBaseUrl() { return get("petstore.baseUrl"); }
    public static String toolshopBaseUrl() { return get("toolshop.baseUrl"); }
    public static String soapCalculatorUrl() { return get("soap.calculatorUrl"); }

    public static String goRestToken()     { return secret("GOREST_TOKEN"); }
}