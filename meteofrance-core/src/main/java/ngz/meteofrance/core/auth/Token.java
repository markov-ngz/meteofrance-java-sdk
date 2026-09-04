package ngz.meteofrance.core.auth;

import java.time.Instant;

/** Represents an authentication token with an expiration time. */
public record Token(String accessToken, Instant expiresAt) {

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
