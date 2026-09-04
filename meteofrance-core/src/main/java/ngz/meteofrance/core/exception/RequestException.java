package ngz.meteofrance.core.exception;

/** Exception thrown when HTTP requests fail. */
public class RequestException extends ApiException {

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
