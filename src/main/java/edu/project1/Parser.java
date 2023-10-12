package edu.project1;

import edu.project1.text.Language;
import edu.project1.text.RusText;
import edu.project1.text.Text;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Parser {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EXIT_SYMBOL = "^D";
    private final Scanner scanner;
    private Text text;

    public Parser(@NotNull Config config, @NotNull InputStream io) {
        this.text = config.getText();
        scanner = new Scanner(io);
    }

    public Parser(@NotNull Config config) {
        this.text = config.getText();
        scanner = new Scanner(System.in);
    }

    void updateLang(Config config) {
        text = config.getText();
    }

    @NotNull
    public Text askUserLang() {
        LOGGER.info(text.chooseLangMsg());
        String maybeLang;
        maybeLang = scanner.nextLine().trim();

        try {
            Text newText = Language.parseLang(maybeLang).getLanguageClass();

            if (newText == null) {
                throw new RuntimeException();
            }

            if (newText instanceof RusText) {
                LOGGER.info(text.rusLanguageDisclaimer());
            }

            return newText;
        } catch (InputMismatchException inputMismatch) {
            LOGGER.info(text.incorrectLanguageMsg());
            throw inputMismatch;
        }
    }

    public boolean askUserAgain() {
        LOGGER.info(text.againMsg());
        String maybeAns;

        maybeAns = scanner.nextLine().trim();

        if (maybeAns.matches("[yY]?")) {
            return true;
        }
        if (maybeAns.matches("[nN]?")) {
            return false;
        }

        LOGGER.info(text.incorrectAgainMsg());
        throw new InputMismatchException();
    }

    @NotNull
    public String askUserLetter() {
        LOGGER.info(text.iterGuessMsg());
        String maybeLetter;

        maybeLetter = scanner.nextLine().trim();

        if (maybeLetter.matches("[a-zA-Z]")) {
            return maybeLetter;
        }

        if (maybeLetter.equals(EXIT_SYMBOL)) {
            LOGGER.info(text.exitMsg());
            System.exit(0);
        }

        LOGGER.info(text.incorrectLetterInputMsg());
        throw new InputMismatchException();
    }
}
