package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Single word sentence")
    void singleWordSentence() {
        String sentence = "Hello";

        String actualValue = Task1.atbash(sentence);
        String expectedValue = "Svool";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Two word sentence, with exclamation mark")
    void twoWordSentence_withExclamationMark() {
        String sentence = "Hello world!";

        String actualValue = Task1.atbash(sentence);
        String expectedValue = "Svool dliow!";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Tinkoff example")
    void tinkoffExample() {
        String sentence = "Any fool can write code that a computer can understand. Good programmers " +
                          "write code that humans can understand. ― Martin Fowler";

        String actualValue = Task1.atbash(sentence);
        String expectedValue = "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih " +
                               "dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi";

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
