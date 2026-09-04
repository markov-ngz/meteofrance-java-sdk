package ngz.meteofrance.core.client;

import java.net.http.HttpClient;
import ngz.meteofrance.core.auth.TokenProvider;
import ngz.meteofrance.core.auth.TokenStore;
import ngz.meteofrance.core.internal.auth.InMemoryTokenStore;
import ngz.meteofrance.core.internal.auth.OAuth2TokenProvider;
import ngz.meteofrance.core.internal.client.MeteoFranceClientImpl;

/** Factory class for creating {@link MeteoFranceClient} instances. */
public class MeteoFranceClientFactory {

    public static MeteoFranceClient create(MeteoFranceClientConfig config) {

        // Create the required dependencies
        HttpClient httpClient = HttpClient.newHttpClient();
        TokenProvider tokenProvider =
                new OAuth2TokenProvider(config.getApplicationId(), httpClient);
        TokenStore tokenStore = new InMemoryTokenStore();

        return new MeteoFranceClientImpl(
                httpClient, tokenProvider, tokenStore, config.getBaseUrl());
    }
}
