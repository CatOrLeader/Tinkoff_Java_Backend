package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.RusText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DictionaryTest {
    @RepeatedTest(5)
    @DisplayName("Get only russian words")
    void getOnlyRussianWords() {
        Dictionary dictionary = Dictionary.defaultDictionary(new RusText());

        boolean actualValue = dictionary.randomWord().matches("^[а-я]+$");

        assertThat(actualValue).isTrue();
    }

    @RepeatedTest(5)
    @DisplayName("Get only english words")
    void getOnlyEnglishWords() {
        Dictionary dictionary = Dictionary.defaultDictionary(new EngText());

        boolean actualValue = dictionary.randomWord().matches("^[a-z]+$");

        assertThat(actualValue).isTrue();
    }

    @Test
    @DisplayName("Correct word saved in the dictionary class")
    void getPrevWord() {
        Dictionary dictionary = Dictionary.defaultDictionary(new EngText());
        String word = dictionary.randomWord();

        String actualWord = dictionary.getLastChosenWord();

        assertThat(actualWord).isEqualTo(word);
    }
}
