package ngz.meteofrance.observation.e2e;

import java.util.HashMap;
import java.util.Map;

public class ApiClientConfig {

    private final String applicationId;

    public ApiClientConfig(EnvironmentConfig environmentConfig) {
        // Load configuration using the environment config
        this.applicationId = environmentConfig.getRequiredString("APPLICATION_ID");
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Map<String, String> asMap() {

        Map<String, String> props = new HashMap<>();
        props.put("applicationid", this.getApplicationId());

        return props;
    }
}
