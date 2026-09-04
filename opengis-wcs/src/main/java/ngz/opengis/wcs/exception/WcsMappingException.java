package ngz.opengis.wcs.exception;

/** Exception thrown when errors occur during WCS object mapping. */
public class WcsMappingException extends WcsException {
    public WcsMappingException(String message) {
        super(message);
    }

    public WcsMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcsMappingException(Throwable cause) {
        super(cause);
    }
}
