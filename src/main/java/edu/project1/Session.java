package edu.project1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Session {
    private int doneMistakes;
    private String word;
    private StringBuilder guessedWord;
    private State state;

    public Session(Config config) {
        doneMistakes = 0;
        word = config.getDictionary().randomWord();
        guessedWord = new StringBuilder("*".repeat(word.length()));
        state = State.CONTINUE;
    }

    public void incrementMistake() {
        doneMistakes++;
    }

    public void win() {
        state = State.WIN;
    }

    public void loose() {
        state = State.LOOSE;
    }
}
