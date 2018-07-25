package co.axelrod.di.exception;

public class NoBeanToInjectException extends RuntimeException {
    public NoBeanToInjectException(String message) {
        super(message);
    }
}
