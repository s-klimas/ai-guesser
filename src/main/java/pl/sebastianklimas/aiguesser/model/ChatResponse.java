package pl.sebastianklimas.aiguesser.model;

public record ChatResponse(String answer, boolean isGuessed, boolean isQuestion) {
}
