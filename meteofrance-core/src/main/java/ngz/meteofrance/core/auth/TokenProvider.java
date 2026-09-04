package ngz.meteofrance.core.auth;

import ngz.meteofrance.core.exception.AuthenticationException;

/** Interface for providing authentication tokens. */
public interface TokenProvider {

    Token fetchToken() throws AuthenticationException;
}
