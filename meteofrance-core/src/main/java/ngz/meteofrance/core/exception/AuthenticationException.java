package ngz.meteofrance.core.exception;

/** Exception thrown when authentication fails. */
public class AuthenticationException extends ApiException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
