package pl.sebastianklimas.aiguesser.model;

public class Game {
    String word;
    int questions;
    boolean guessed;

    public Game(String word) {
        this.word = word;
        this.questions = 0;
        this.guessed = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public boolean isGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }

    public void incrementQuestions() {
        this.questions++;
    }
}
