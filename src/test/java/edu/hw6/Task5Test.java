package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task5.HackerNews;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Grep not existing title")
    void grepNotExistingTitle() {
        long id = -100;

        var actualTitle = HackerNews.news(id);

        assertThat(actualTitle).isEmpty();
    }

    @Test
    @DisplayName("Grep existing title")
    void grepExistingTitle() {
        long id = 37570037;

        var actualTitle = HackerNews.news(id).orElseThrow();
        var expectedValue = "JDK 21 Release Notes";

        assertThat(actualTitle).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Grep ids")
    void grepIds() {
        var actualTitle = HackerNews.hackerNewsTopStories();

        assertThat(actualTitle).isNotEmpty();
    }
}
