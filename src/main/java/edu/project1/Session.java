package edu.project1;

import edu.project1.text.Text;

public final class Session {
    public static final int MAX_MISTAKES = 5;
    private final String word;
    private Text text;
    private State state;
    private String guessedWord;
    private int mistakes;

    public Session(Text text) {
        Dictionary dictionary = Dictionary.defaultDictionary(text);
        this.text = text;
        word = dictionary.randomWord();
        state = State.CONTINUE;
        guessedWord = "*".repeat(dictionary.getLastChosenWord().length());
        mistakes = 0;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public String getWord() {
        return word;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
        if (isWordGuessed()) {
            state = State.WIN;
        }
    }

    public State getState() {
        return state;
    }

    public void incrementMistakes() {
        mistakes++;
        if (mistakes == MAX_MISTAKES) {
            if (isWordGuessed()) {
                state = State.WIN;
            } else {
                state = State.LOOSE;
            }

            return;
        }
        state = State.CONTINUE;
    }

    private boolean isWordGuessed() {
        return word.contentEquals(guessedWord);
    }

    public int getMistakes() {
        return mistakes;
    }
}
