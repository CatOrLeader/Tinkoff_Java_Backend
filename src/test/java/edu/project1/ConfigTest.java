package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.RusText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfigTest {
    @Test
    @DisplayName("Changing language to rus")
    void changingToRus() {
        Config config = new Config();

        config.setLang(new RusText());
        boolean actualValue = config.getText() instanceof RusText;
        boolean expectedValue = true;

        // I do not want to use assertTrue because it can hide the original intentions
        org.assertj.core.api.Assertions.assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Changing language to eng")
    void changingToEng() {
        Config config = new Config();

        config.setLang(new EngText());
        boolean actualValue = config.getText() instanceof EngText;
        boolean expectedValue = true;

        // I do not want to use assertTrue because it can hide the original intentions
        org.assertj.core.api.Assertions.assertThat(actualValue).isEqualTo(expectedValue);
    }
}
