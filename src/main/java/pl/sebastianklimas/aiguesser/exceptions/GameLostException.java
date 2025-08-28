package pl.sebastianklimas.aiguesser.exceptions;

public class GameLostException extends RuntimeException {
    public GameLostException(String message) {
        super(message);
    }
}
