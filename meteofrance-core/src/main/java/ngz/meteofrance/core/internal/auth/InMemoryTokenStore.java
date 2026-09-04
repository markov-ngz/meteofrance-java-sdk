package ngz.meteofrance.core.internal.auth;

import java.util.Optional;
import ngz.meteofrance.core.auth.Token;
import ngz.meteofrance.core.auth.TokenStore;

/** In-memory implementation of TokenStore. */
public class InMemoryTokenStore implements TokenStore {

    private volatile Token token;

    @Override
    public Optional<Token> get() {
        return Optional.ofNullable(token);
    }

    @Override
    public void store(Token token) {
        this.token = token;
    }

    @Override
    public void clear() {
        this.token = null;
    }
}
