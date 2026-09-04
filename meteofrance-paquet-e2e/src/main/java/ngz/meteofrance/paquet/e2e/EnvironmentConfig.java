package ngz.meteofrance.paquet.e2e;

import java.util.Properties;

public class EnvironmentConfig {

    private final Properties properties;

    public EnvironmentConfig() {
        this.properties = new Properties();
        loadEnvironmentVariables();
    }

    public EnvironmentConfig(Properties customProperties) {
        this.properties = new Properties(customProperties);
    }

    private void loadEnvironmentVariables() {
        // Environment variables
        String appId = System.getenv("APPLICATION_ID");
        if (appId != null) {
            properties.setProperty("APPLICATION_ID", appId);
        }
    }

    /**
     * Gets a string property value.
     *
     * @param key The property key
     * @return The property value, or null if not found
     */
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets a string property value with a default value.
     *
     * @param key The property key
     * @param defaultValue The default value to return if key is not found
     * @return The property value, or defaultValue if not found
     */
    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Gets a required string property value.
     *
     * @param key The property key
     * @return The property value
     * @throws IllegalStateException if the property is not found or is empty
     */
    public String getRequiredString(String key) {
        String value = getString(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Required property '" + key + "' is missing or empty");
        }
        return value;
    }

    /**
     * Gets an integer property value.
     *
     * @param key The property key
     * @return The integer value
     * @throws NumberFormatException if the property is not a valid integer
     * @throws IllegalStateException if the property is missing
     */
    public int getInt(String key) {
        String value = getRequiredString(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    "Property '" + key + "' must be a valid integer, but was: " + value);
        }
    }

    /**
     * Gets a boolean property value.
     *
     * @param key The property key
     * @return The boolean value
     * @throws IllegalStateException if the property is missing
     */
    public boolean getBoolean(String key) {
        String value = getRequiredString(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * Checks if a property exists.
     *
     * @param key The property key
     * @return true if the property exists, false otherwise
     */
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}
