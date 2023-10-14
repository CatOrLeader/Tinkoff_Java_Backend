package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task7.rotateLeft;
import static edu.hw1.Task7.rotateRight;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Rotate incorrect n")
    void incorrectN() {
        int n = 0;

        int actualValLeft = rotateLeft(n, 3);
        int expectedValLeft = -1;


        int actualValRight = rotateRight(n, 3);
        int expectedValRight = -1;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
        assertThat(actualValRight).isEqualTo(expectedValRight);
    }

    @Test
    @DisplayName("Rotate incorrect shift")
    void incorrectShift() {
        int shift = 0;

        int actualValLeft = rotateLeft(128, shift);
        int expectedValLeft = -1;


        int actualValRight = rotateRight(128, shift);
        int expectedValRight = -1;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
        assertThat(actualValRight).isEqualTo(expectedValRight);
    }

    @Test
    @DisplayName("Rotate right 1")
    void rotateRight1() {
        int n = 8;
        int shift = 1;

        int actualValLeft = rotateRight(n, shift);
        int expectedValLeft = 4;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate right 2")
    void rotateRight2() {
        int n = 17;
        int shift = 2;

        int actualValLeft = rotateRight(n, shift);
        int expectedValLeft = 12;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate right 3")
    void rotateRight3() {
        int n = 1;
        int shift = 2;

        int actualValLeft = rotateRight(n, shift);
        int expectedValLeft = 1;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate right 4")
    void rotateRight4() {
        int n = 23;
        int shift = 7;

        int actualValLeft = rotateRight(n, shift);
        int expectedValLeft = 29;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate left 1")
    void rotateLeft1() {
        int n = 16;
        int shift = 1;

        int actualValLeft = rotateLeft(n, shift);
        int expectedValLeft = 1;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate left 2")
    void rotateLeft2() {
        int n = 17;
        int shift = 2;

        int actualValLeft = rotateLeft(n, shift);
        int expectedValLeft = 6;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate left 3")
    void rotateLeft3() {
        int n = 1;
        int shift = 2;

        int actualValLeft = rotateLeft(n, shift);
        int expectedValLeft = 1;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }

    @Test
    @DisplayName("Rotate left 4")
    void rotateLeft4() {
        int n = 23;
        int shift = 7;

        int actualValLeft = rotateRight(n, shift);
        int expectedValLeft = 29;

        assertThat(actualValLeft).isEqualTo(expectedValLeft);
    }
}
