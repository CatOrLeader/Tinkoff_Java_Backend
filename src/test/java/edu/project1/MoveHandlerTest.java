package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.RusText;
import edu.project1.text.Text;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MoveHandlerTest {
    @Test
    @DisplayName("Setup correct language for the game")
    void setupLanguageForTheGame() {
        String input = "dsa\ndsad\nrus\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MoveHandler moveHandler = new MoveHandler(inputStream);

        Text actualValue = moveHandler.getText();

        assertThat(actualValue).isExactlyInstanceOf(RusText.class);
    }

    @Test
    @DisplayName("Correct letter provided")
    void correctLetterProvided() {
        Session session = new Session(new EngText());
        String word = session.getWord();
        String input = "dsa\ndsad\neng\n" + word.charAt(0) + "\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MoveHandler moveHandler = new MoveHandler(inputStream);
        moveHandler.setSession(session);

        int expectedMistakes = session.getMistakes();
        moveHandler.askLetter();
        int actualMistakes = session.getMistakes();

        assertThat(actualMistakes).isEqualTo(expectedMistakes);
    }

    @Test
    @DisplayName("Incorrect letter provided")
    void incorrectLetterProvided() {
        Session session = new Session(new EngText());
        String input = "dsa\ndsad\neng\nz\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MoveHandler moveHandler = new MoveHandler(inputStream);
        moveHandler.setSession(session);

        int expectedMistakes = session.getMistakes() + 1;
        moveHandler.askLetter();
        int actualMistakes = session.getMistakes();

        assertThat(actualMistakes).isEqualTo(expectedMistakes);
    }

    @Test
    @DisplayName("Correct again choice provided, yes")
    void correctAgainChoiceProvidedYes() {
        String input = "dsa\neng\ny\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MoveHandler moveHandler = new MoveHandler(inputStream);

        boolean actualMistakes = moveHandler.askAgain();

        assertThat(actualMistakes).isTrue();
    }

    @Test
    @DisplayName("Correct again choice provided, no")
    void correctAgainChoiceProvidedNo() {
        String input = "dsa\neng\nn\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        MoveHandler moveHandler = new MoveHandler(inputStream);

        boolean actualMistakes = moveHandler.askAgain();

        assertThat(actualMistakes).isFalse();
    }
}
