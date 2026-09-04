package ngz.meteofrance.core.exception;

/** Raised when an error is related to the MeteoFrance API. */
public class ApiException extends MeteoFranceRuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
