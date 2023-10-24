package edu.hw3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.InputMismatchException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Correct initials fulfilled, ascending")
    void correctInitialsFulfilled_asc() {
        String[] initials = new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String sortingType = "ASC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of(
            "Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke"
        );

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Correct initials fulfilled, desc")
    void correctInitialsFulfilled_desc() {
        String[] initials = new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"};
        String sortingType = "DESC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of(
            "Carl Gauss", "Leonhard Euler", "Paul Erdos"
        );

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Correct initials not fulfilled, ascending")
    void correctInitialsNotFulfilled_asc() {
        String[] initials = new String[] {"John", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String sortingType = "ASC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of(
            "Thomas Aquinas", "Rene Descartes", "David Hume", "John"
        );

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Correct initials not fulfilled, ascending")
    void correctInitialsNotFulfilled_desc() {
        String[] initials = new String[] {"John", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String sortingType = "DESC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of(
            "Thomas Aquinas", "Rene Descartes", "David Hume", "John"
        ).reversed();

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Empty array provided")
    void emptyArrayProvided() {
        String[] initials = new String[] {};
        String sortingType = "DESC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of();

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Null array provided")
    void nullArrayProvided() {
        String[] initials = null;
        String sortingType = "DESC";

        List<String> actualInitials = Task5.parseContacts(initials, sortingType);
        List<String> expectedInitials = List.of();

        assertThat(actualInitials).containsExactlyElementsOf(expectedInitials);
    }

    @Test
    @DisplayName("Incorrect sorting type provided")
    void incorrectSortingTypeProvided() {
        String[] initials = new String[] {"aa", "bb"};
        String sortingType = "asd";

        Assertions.assertThrows(InputMismatchException.class, () -> Task5.parseContacts(initials, sortingType));
    }
}
