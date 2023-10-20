package edu.project1;

import edu.project1.text.Text;
import java.io.InputStream;
import static edu.project1.Utils.LOGGER;

public final class HangmanGame {
    private final MoveHandler moveHandler;
    private Session session;
    private Text text;

    public HangmanGame(InputStream inputStream) {
        moveHandler = new MoveHandler(inputStream);
        setupSession();
    }

    private void setupSession() {
        session = new Session(moveHandler.getText());
        moveHandler.setSession(session);
        text = session.getText();
    }

    public void run() {
        LOGGER.info(text.greetingsMsg());
        while (true) {
            startGame();
            if (!moveHandler.askAgain()) {
                LOGGER.info(text.exitMsg());
                return;
            }
            setupSession();
        }
    }

    private void startGame() {
        while (session.getState() == State.CONTINUE) {
            moveHandler.askLetter();
            LOGGER.info(text.iterStepMsg(
                session.getGuessedWord().substring(0, 1).toUpperCase()
                + session.getGuessedWord().substring(1),
                session.getMistakes(),
                Session.MAX_MISTAKES
            ));
        }

        switch (session.getState()) {
            case WIN -> LOGGER.info(text.winMsg());
            case LOOSE -> LOGGER.info(text.looseMsg());
            default -> LOGGER.info("Error occurred. State of the game is incorrect at the end.");
        }
    }
}
