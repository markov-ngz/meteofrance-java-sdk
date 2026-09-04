package ngz.meteofrance.core.exception;

/** Base exception class for Météo-France API related errors. */
public class MeteoFranceException extends Exception {

    public MeteoFranceException(String message) {
        super(message);
    }

    public MeteoFranceException(Throwable cause) {
        super(cause);
    }

    public MeteoFranceException(String message, Throwable cause) {
        super(message, cause);
    }
}
