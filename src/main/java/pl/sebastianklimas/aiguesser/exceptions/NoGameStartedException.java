package pl.sebastianklimas.aiguesser.exceptions;

public class NoGameStartedException extends RuntimeException {
    public NoGameStartedException(String message) {
        super(message);
    }
}
