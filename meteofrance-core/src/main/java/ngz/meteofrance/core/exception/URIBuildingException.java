package ngz.meteofrance.core.exception;

public class URIBuildingException extends MeteoFranceRuntimeException {

    public URIBuildingException(String message) {
        super(message);
    }

    public URIBuildingException(Throwable cause) {
        super(cause);
    }

    public URIBuildingException(String message, Throwable cause) {
        super(message, cause);
    }
}
