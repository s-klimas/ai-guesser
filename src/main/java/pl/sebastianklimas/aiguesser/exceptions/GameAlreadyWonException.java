package pl.sebastianklimas.aiguesser.exceptions;

public class GameAlreadyWonException extends RuntimeException {
    public GameAlreadyWonException(String message) {
        super(message);
    }
}
