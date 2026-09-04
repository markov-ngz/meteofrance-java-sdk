package ngz.opengis.wcs.exception;

/** Exception thrown when errors occur during WCS XML parsing. */
public class WcsParsingException extends WcsException {

    public WcsParsingException(String message) {
        super(message);
    }

    public WcsParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcsParsingException(Throwable cause) {
        super(cause);
    }
}
