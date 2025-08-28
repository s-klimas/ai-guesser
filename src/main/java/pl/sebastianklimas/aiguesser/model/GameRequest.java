package pl.sebastianklimas.aiguesser.model;

public record GameRequest(
        String message,
        Game game
) {
}
