package edu.project1;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    @DisplayName("Incorrect language input with single word")
    void incorrectLangInputSingleWord() {
        String userInput = "\nincorrect\n";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserLang);
    }

    @Test
    @DisplayName("Incorrect language input with multiple words")
    void incorrectLangInputMultipleWords() {
        String userInput = "incorrect input";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserLang);
    }

    @Test
    @DisplayName("Incorrect language input with blank")
    void incorrectLangBlank() {
        String userInput = "";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(NoSuchElementException.class, parser::askUserLang);
    }

    @Test
    @DisplayName("Incorrect again choice input with single word")
    void incorrectAgainSingleWord() {
        String userInput = "incorrect";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserAgain);
    }

    @Test
    @DisplayName("Incorrect again choice input with multiple word")
    void incorrectAgainMultipleWords() {
        String userInput = "incorrect input";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserAgain);
    }

    @Test
    @DisplayName("Incorrect again choice input with blank")
    void incorrectAgainBlank() {
        String userInput = "";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(NoSuchElementException.class, parser::askUserAgain);
    }

    @Test
    @DisplayName("Incorrect letter input with single word")
    void incorrectLetterSingleWord() {
        String userInput = "incorrect";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserLetter);
    }

    @Test
    @DisplayName("Incorrect letter input with multiple word")
    void incorrectLetterMultipleWord() {
        String userInput = "incorrect input";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(InputMismatchException.class, parser::askUserLetter);
    }

    @Test
    @DisplayName("Incorrect letter input with blank")
    void incorrectLetterBlank() {
        String userInput = "";
        InputStream io = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(io);
        Parser parser = new Parser(new Config(), io);

        assertThrows(NoSuchElementException.class, parser::askUserLetter);
    }
}
