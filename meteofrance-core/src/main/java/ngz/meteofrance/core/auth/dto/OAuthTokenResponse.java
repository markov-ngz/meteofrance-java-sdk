package ngz.meteofrance.core.auth.dto;

import ngz.markov.com.fasterxml.jackson.annotation.JsonProperty;

/** Token received payload. */
public record OAuthTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("scope") String scope,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") long expiresIn) {}
