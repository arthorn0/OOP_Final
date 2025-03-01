package Exceptions;

public class PropertyAlreadyExitsException extends RuntimeException {
    public PropertyAlreadyExitsException(String message) {
        super(message);
    }
}
