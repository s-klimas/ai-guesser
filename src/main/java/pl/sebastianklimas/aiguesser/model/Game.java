package pl.sebastianklimas.aiguesser.model;

import lombok.Data;

@Data
public class Game {
    String word;
    int prompts;
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
