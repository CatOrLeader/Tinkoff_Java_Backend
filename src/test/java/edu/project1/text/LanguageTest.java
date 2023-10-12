package edu.project1.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.InputMismatchException;

public class LanguageTest {
    @Test
    @DisplayName("Incorrect input with single word")
    void incorrectInputSingleWord() {
        String input = "incorrect";

        Assertions.assertThrows(InputMismatchException.class, () -> Language.parseLang(input));
    }

    @Test
    @DisplayName("Incorrect input with single word")
    void incorrectInputMultipleWord() {
        String input = "incorrect input";

        Assertions.assertThrows(InputMismatchException.class, () -> Language.parseLang(input));
    }

    @Test
    @DisplayName("Incorrect input with blank")
    void incorrectInputBlank() {
        String input = "";

        Assertions.assertThrows(InputMismatchException.class, () -> Language.parseLang(input));
    }

    @Test
    @DisplayName("Correct input for rus")
    void correctInputRus() {
        String input = "rus";

        Language actualLang = Language.parseLang(input);
        Language expectedLang = Language.RUS;

        org.assertj.core.api.Assertions.assertThat(actualLang).isEqualTo(expectedLang);
    }

    @Test
    @DisplayName("Correct input for eng")
    void correctInputEng() {
        String input = "eng";

        Language actualLang = Language.parseLang(input);
        Language expectedLang = Language.ENG;

        org.assertj.core.api.Assertions.assertThat(actualLang).isEqualTo(expectedLang);
    }
}
