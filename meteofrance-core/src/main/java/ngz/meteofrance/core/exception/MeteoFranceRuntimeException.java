package ngz.meteofrance.core.exception;

/** Base exception class for Météo-France API related errors. */
public class MeteoFranceRuntimeException extends RuntimeException {

    public MeteoFranceRuntimeException(String message) {
        super(message);
    }

    public MeteoFranceRuntimeException(Throwable cause) {
        super(cause);
    }

    public MeteoFranceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
