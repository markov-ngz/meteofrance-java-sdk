package ngz.meteofrance.core.client;

import java.util.Objects;

public class MeteoFranceClientConfig {

    private final String applicationId;
    private final String baseUrl;

    // 1. Map ALL fields from the builder to the class
    private MeteoFranceClientConfig(Builder builder) {
        this.applicationId = builder.applicationId;
        this.baseUrl = builder.baseUrl;
    }

    public String getApplicationId() {
        return applicationId;
    }

    // Expose the baseUrl getter
    public String getBaseUrl() {
        return baseUrl;
    }

    // Entry point for the builder forcing the required parameter
    public static Builder builder(String applicationId) {
        return new Builder(applicationId);
    }

    public static class Builder {
        private final String applicationId;
        // 2. Set the default value for the optional field here
        private String baseUrl = "https://public-api.meteofrance.fr";

        private Builder(String applicationId) {
            this.applicationId =
                    Objects.requireNonNull(applicationId, "applicationId cannot be null");
        }

        // 3. Make it public, assign the value, and return 'this' for chaining
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl cannot be null");
            return this;
        }

        public MeteoFranceClientConfig build() {
            return new MeteoFranceClientConfig(this);
        }
    }
}
