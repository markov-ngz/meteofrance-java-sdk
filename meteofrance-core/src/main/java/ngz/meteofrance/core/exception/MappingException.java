package ngz.meteofrance.core.exception;

public class MappingException extends MeteoFranceRuntimeException {

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
