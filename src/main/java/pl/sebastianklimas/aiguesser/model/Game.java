package pl.sebastianklimas.aiguesser.model;

public class Game {
    String word;
    int prompts;
    boolean guessed;

    public Game(String word) {
        this.word = word;
        this.prompts = 0;
        this.guessed = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPrompts() {
        return prompts;
    }

    public void setPrompts(int prompts) {
        this.prompts = prompts;
    }

    public boolean isGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }

    public void incrementPrompts() {
        this.prompts++;
    }
}
