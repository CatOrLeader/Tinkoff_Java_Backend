package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.RusText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;
import static org.assertj.core.api.Assertions.assertThat;

public class UserInputParserTest {
    @Test
    @DisplayName("Parse correct language")
    void parseCorrectLanguage() {
        String input = "rus\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertDoesNotThrow(parser::parseLang);
    }

    @Test
    @DisplayName("Parse incorrect language")
    void parseIncorrectLanguage() {
        String input = "lol\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(InputMismatchException.class, parser::parseLang);
    }

    @Test
    @DisplayName("Parse exit in language parsing")
    void parseExitInLanguage() {
        String input = "^D\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(ExitMessageProvided.class, parser::parseLang);
    }

    @Test
    @DisplayName("Parse correct english letter")
    void parseCorrectEngLetter() throws ExitMessageProvided {
        String input = "Q\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        String actualLetter = parser.parseLetter(new EngText());
        String expectedLetter = "q";

        assertThat(actualLetter).isEqualTo(expectedLetter);
    }

    @Test
    @DisplayName("Parse correct russian letter")
    void parseCorrectRusLetter() throws ExitMessageProvided {
        String input = "Я\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        String actualLetter = parser.parseLetter(new RusText());
        String expectedLetter = "я";

        assertThat(actualLetter).isEqualTo(expectedLetter);
    }

    @Test
    @DisplayName("Parse incorrect letter")
    void parseIncorrectLetter() {
        String input = "lol\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(InputMismatchException.class, () -> parser.parseLetter(new EngText()));
    }

    @Test
    @DisplayName("Parse exit in letter parsing")
    void parseExitInLetter() {
        String input = "^D\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(ExitMessageProvided.class, () -> parser.parseLetter(new EngText()));
    }

    @Test
    @DisplayName("Parse correct again choice yes")
    void parseCorrectAgainChoiceYes() throws ExitMessageProvided {
        String input = "y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        boolean actualValue = parser.parseAgainChoice();

        assertThat(actualValue).isTrue();
    }

    @Test
    @DisplayName("Parse correct again choice no")
    void parseCorrectAgainChoiceNo() throws ExitMessageProvided {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        boolean actualValue = parser.parseAgainChoice();

        assertThat(actualValue).isFalse();
    }

    @Test
    @DisplayName("Parse incorrect again choice")
    void parseIncorrectAgainChoice() {
        String input = "lol\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(InputMismatchException.class, parser::parseAgainChoice);
    }

    @Test
    @DisplayName("Parse exit in again choice parsing")
    void parseExitInAgainChoice() {
        String input = "^D\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        UserInputParser parser = new UserInputParser(inputStream);

        Assertions.assertThrows(ExitMessageProvided.class, parser::parseAgainChoice);
    }
}
