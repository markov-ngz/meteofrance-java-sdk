package ngz.meteofrance.core.internal.auth;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import ngz.markov.tools.jackson.databind.ObjectMapper;
import ngz.markov.tools.jackson.databind.exc.MismatchedInputException;
import ngz.meteofrance.core.auth.Token;
import ngz.meteofrance.core.auth.TokenProvider;
import ngz.meteofrance.core.auth.dto.OAuthTokenResponse;
import ngz.meteofrance.core.exception.AuthenticationException;

/** OAuth2 implementation of TokenProvider for fetching tokens from Météo-France API. */
public class OAuth2TokenProvider implements TokenProvider {

    private static final String AUTH_URL = "https://portail-api.meteofrance.fr/token";

    private final String applicationId;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OAuth2TokenProvider(String applicationId, HttpClient httpClient) {

        this.applicationId = applicationId;
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Token fetchToken() throws AuthenticationException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(AUTH_URL))
                        .header("accept", "*/*")
                        .header("Authorization", "Basic " + applicationId)
                        .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                        .build();

        try {

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new AuthenticationException(
                        "Authentication failed with status " + response.statusCode());
            }

            OAuthTokenResponse tokenResponse =
                    objectMapper.readValue(response.body(), OAuthTokenResponse.class);

            return new Token(
                    tokenResponse.accessToken(),
                    Instant.now().plusSeconds(tokenResponse.expiresIn() - 30));

        } catch (IOException | InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new AuthenticationException("Failed to fetch OAuth2 token", e);

        } catch (MismatchedInputException e) {

            Thread.currentThread().interrupt();

            throw new AuthenticationException("Invalid OAuth2 response payload", e);
        }
    }
}
