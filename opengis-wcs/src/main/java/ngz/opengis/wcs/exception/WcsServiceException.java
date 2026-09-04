package ngz.opengis.wcs.exception;

/** Exception thrown when errors occur during WCS XML parsing. */
public class WcsServiceException extends WcsException {

    public WcsServiceException(String message) {
        super(message);
    }

    public WcsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcsServiceException(Throwable cause) {
        super(cause);
    }
}
