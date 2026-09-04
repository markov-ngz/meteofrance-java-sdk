package ngz.meteofrance.core.internal.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import ngz.meteofrance.core.auth.Token;
import ngz.meteofrance.core.auth.TokenProvider;
import ngz.meteofrance.core.auth.TokenStore;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of MeteoFranceClient for making authenticated requests to the Météo-France API.
 */
public class MeteoFranceClientImpl implements MeteoFranceClient {

    private final HttpClient httpClient;
    private final TokenProvider tokenProvider;
    private final TokenStore tokenStore;
    private final String baseUrl;

    private static final Logger LOG = LoggerFactory.getLogger(MeteoFranceClientImpl.class);

    public MeteoFranceClientImpl(
            HttpClient httpClient,
            TokenProvider tokenProvider,
            TokenStore tokenStore,
            String baseUrl) {
        this.httpClient = httpClient;
        this.tokenProvider = tokenProvider;
        this.tokenStore = tokenStore;
        this.baseUrl = baseUrl;
    }

    @Override
    public <T> HttpResponse<T> request(
            String endpoint, HttpResponse.BodyHandler<T> responseHandler) {

        String url = resolveUrl(endpoint);

        try {

            // Fetch Token and send first request
            Token token = resolveToken();

            LOG.info("Sending request to : {}", url);

            HttpResponse<T> response = sendRequest(url, token.accessToken(), responseHandler);

            LOG.info("Received response with status code : {}", response.statusCode());

            if (response.statusCode() == 200) {
                return response;
            } else if (response.statusCode() != 401) {
                throw new RequestException(
                        "Request failed with status code :" + response.statusCode());
            }

            LOG.info("Renewing token as 401 status code was received");

            tokenStore.clear();

            token = resolveToken();

            return sendRequest(url, token.accessToken(), responseHandler);

        } catch (IOException | InterruptedException e) {
            throw new RequestException("Failed to execute the request", e);
        }
    }

    private String resolveUrl(String endpoint) {

        // handle "/"
        String sanitizedBase = this.baseUrl.endsWith("/") ? this.baseUrl : this.baseUrl + "/";
        String sanitizedEndpoint = endpoint.startsWith("/") ? endpoint.substring(1) : endpoint;

        return URI.create(sanitizedBase).resolve(sanitizedEndpoint).toString();
    }

    private Token resolveToken() {

        return tokenStore
                .get()
                .filter(token -> !token.isExpired())
                .orElseGet(
                        () -> {
                            Token fresh = tokenProvider.fetchToken();
                            tokenStore.store(fresh);
                            return fresh;
                        });
    }

    private <T> HttpResponse<T> sendRequest(
            String url, String accessToken, HttpResponse.BodyHandler<T> responseHandler)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + accessToken);

        return httpClient.send(builder.build(), responseHandler);
    }
}
