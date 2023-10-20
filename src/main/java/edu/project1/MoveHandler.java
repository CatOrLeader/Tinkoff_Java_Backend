package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.Text;
import java.io.InputStream;
import java.util.InputMismatchException;
import static edu.project1.Utils.LOGGER;

public final class MoveHandler {
    private final UserInputParser parser;
    private Session session;
    private Text text;

    public MoveHandler(InputStream inputStream) {
        text = new EngText();
        parser = new UserInputParser(inputStream);
        askLang();
    }

    public void askLang() {
        LOGGER.info(text.chooseLangMsg());
        while (true) {
            try {
                text = parser.parseLang();
                break;
            } catch (ExitMessageProvided exitMessageProvided) {
                abnormalExit();
            } catch (InputMismatchException exception) {
                LOGGER.info(text.incorrectLanguageMsg());
            }
        }
        LOGGER.info(text.languageChangedSuccessfully());
    }

    public void askLetter() {
        LOGGER.info(text.iterGuessMsg());
        String letter;
        while (true) {
            try {
                letter = parser.parseLetter(text);
                break;
            } catch (ExitMessageProvided exitMessageProvided) {
                abnormalExit();
            } catch (InputMismatchException exception) {
                LOGGER.info(text.incorrectLetterInputMsg());
            }
        }

        if (session.getWord().contains(letter)) {
            correctLetterProvided(letter);
        } else {
            incorrectLetterProvided();
        }
    }

    public boolean askAgain() {
        LOGGER.info(text.againMsg());
        boolean choice;
        while (true) {
            try {
                choice = parser.parseAgainChoice();
                break;
            } catch (ExitMessageProvided exitMessageProvided) {
                abnormalExit();
            } catch (InputMismatchException exception) {
                LOGGER.info(text.incorrectAgainMsg());
            }
        }

        return choice;
    }

    private void abnormalExit() {
        LOGGER.info(text.abnormalExitMsg());
        System.exit(0);
    }

    private void correctLetterProvided(String letter) {
        LOGGER.info(text.guessCorrectMsg());
        String word = session.getWord();
        StringBuilder stringBuilder = new StringBuilder(session.getGuessedWord());
        char character = letter.charAt(0);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == character) {
                stringBuilder.setCharAt(i, character);
            }
        }
        session.setGuessedWord(stringBuilder.toString());
    }

    private void incorrectLetterProvided() {
        LOGGER.info(text.guessWrongMsg());
        session.incrementMistakes();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Text getText() {
        return text;
    }
}
