package ngz.meteofrance.core.auth;

import java.util.Optional;

/** Interface for storing and retrieving authentication tokens. */
public interface TokenStore {

    Optional<Token> get();

    void store(Token token);

    void clear();
}
