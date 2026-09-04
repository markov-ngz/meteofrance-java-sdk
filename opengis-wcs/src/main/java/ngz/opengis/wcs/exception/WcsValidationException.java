package ngz.opengis.wcs.exception;

public class WcsValidationException extends WcsException {

    public WcsValidationException(String message) {
        super(message);
    }

    public WcsValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcsValidationException(Throwable cause) {
        super(cause);
    }
}
