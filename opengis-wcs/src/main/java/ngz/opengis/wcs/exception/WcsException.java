package ngz.opengis.wcs.exception;

public abstract class WcsException extends Exception {

    public WcsException(String message) {
        super(message);
    }

    public WcsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WcsException(Throwable cause) {
        super(cause);
    }
}
