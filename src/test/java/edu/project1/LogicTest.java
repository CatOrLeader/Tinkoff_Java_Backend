package edu.project1;

import edu.project1.text.RusText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

public class LogicTest {
    @Test
    @DisplayName("Changing language test, correct")
    void changeLangCorrect() {
        String input = "rus";
        var io = new ByteArrayInputStream(input.getBytes());
        Logic logic = new Logic(io);

        logic.setupLang();
        boolean actualValue = logic.getText() instanceof RusText;
        boolean expectedValue = true;

        org.assertj.core.api.Assertions.assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Changing language test, incorrect")
    void changeLangIncorrect() {
        String input = "eng";
        var io = new ByteArrayInputStream(input.getBytes());
        Logic logic = new Logic(io);

        logic.setupLang();
        boolean actualValue = logic.getText() instanceof RusText;
        boolean expectedValue = false;

        org.assertj.core.api.Assertions.assertThat(actualValue).isEqualTo(expectedValue);
    }
}
