package pl.sebastianklimas.aiguesser.model;

public record Response(String message, boolean won, int promptsUsed) {
}
