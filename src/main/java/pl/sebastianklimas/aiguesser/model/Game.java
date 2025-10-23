package pl.sebastianklimas.aiguesser.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Game {
    @Schema(name = "word", example = "butter", maxLength = 255, description = "The word to guess")
    String word;
    @Schema(name = "prompts", example = "1", maxLength = 255, description = "Amount of question asked")
    int prompts;
    @Schema(name = "guessed", example = "false", maxLength = 255, description = "Is the word guessed")
    boolean guessed;

    public Game(String word) {
        this.word = word;
        this.prompts = 0;
        this.guessed = false;
    }

    public void incrementPrompts() {
        this.prompts++;
    }
}
