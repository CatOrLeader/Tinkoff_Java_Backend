package edu.project1;

import edu.project1.text.Text;
import java.io.InputStream;
import java.util.InputMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class Logic {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Parser parser;
    private final Config config;
    private Session session;

    public Logic() {
        config = new Config();
        parser = new Parser(config);
        session = new Session(config);
    }

    public Logic(InputStream io) {
        config = new Config();
        parser = new Parser(config, io);
        session = new Session(config);
    }

    public Logic(@NotNull Dictionary dictionary) {
        config = new Config(dictionary);
        parser = new Parser(config);
        session = new Session(config);
    }

    @NotNull
    public Text getText() {
        return config.getText();
    }

    public void setupLang() {
        try {
            config.setLang(parser.askUserLang());
            parser.updateLang(config);
        } catch (InputMismatchException mismatchException) {
            setupLang();
        }
    }

    private boolean isWin() {
        String word = session.getWord();
        StringBuilder guessedWord = session.getGuessedWord();

        return word.contentEquals(guessedWord);
    }

    private boolean isLoose() {
        return session.getDoneMistakes() == Config.MAX_MISTAKES;
    }

    private void processUserChoice(boolean isAgain) {
        Text text = config.getText();

        if (isAgain) {
            start();
        } else {
            LOGGER.info(text.exitMsg());
        }
    }

    @NotNull
    private Session processLetter(String letter) {
        Text text = config.getText();

        String word = session.getWord();
        StringBuilder guessedWord = session.getGuessedWord();
        String lowercaseWord = word.toLowerCase();
        String lowercaseLetter = letter.toLowerCase();

        if (lowercaseWord.contains(lowercaseLetter)) {
            LOGGER.info(text.guessCorrectMsg());

            for (int i = 0; i < lowercaseWord.length(); i++) {
                if (lowercaseWord.charAt(i) == lowercaseLetter.charAt(0)) {
                    guessedWord.setCharAt(i, word.charAt(i));
                }
            }

            if (isWin()) {
                session.win();
            }
        } else {
            LOGGER.info(text.guessWrongMsg());

            session.incrementMistake();

            if (isLoose()) {
                session.loose();
            }
        }

        return session;
    }

    @NotNull
    private Session iterate() {
        try {
            String letter = parser.askUserLetter();
            return processLetter(letter);
        } catch (InputMismatchException input) {
            iterate();
        }

        return session;
    }

    private void initialise() {
        session = new Session(config);
    }

    private void maybeRestart() throws InputMismatchException {
        boolean isAgain = parser.askUserAgain();
        processUserChoice(isAgain);
    }

    void start() {
        initialise();

        Text text = config.getText();

        while (session.getState() == State.CONTINUE) {
            session = iterate();
            LOGGER.info(text.iterStepMsg(
                session.getGuessedWord().toString(),
                session.getDoneMistakes(),
                Config.MAX_MISTAKES
            ));
        }

        if (session.getState() == State.WIN) {
            LOGGER.info(text.winMsg());
        } else {
            LOGGER.info(text.looseMsg());
        }

        try {
            maybeRestart();
        } catch (InputMismatchException mismatchException) {
            maybeRestart();
        }
    }
}
