package edu.project1;

import edu.project1.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class HangmanGame {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Logic logic;

    public HangmanGame() {
        logic = new Logic();
    }

    public HangmanGame(@NotNull Dictionary dict) {
        logic = new Logic(dict);
    }

    public void run() {
        Text text = logic.getText();
        try {
            LOGGER.info(text.greetingsMsg());
            logic.setupLang();
            logic.start();
        } catch (Exception any) {
            LOGGER.info(text.abnormalExitMsg());
        }
    }
}
