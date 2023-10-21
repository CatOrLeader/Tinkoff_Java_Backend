package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SquareTest {
    @Test
    @DisplayName("Correct square values passed as Square, single assignment")
    void correctValuesAsSquareSingleAssignment() {
        double size = 2;

        double actualValue = new Square().setSides(size).area();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct square values passed as Square, double assignment")
    void correctValuesAsSquareDoubleAssignment() {
        double width = 3;
        double height = 3;

        double actualValue = new Square().setWidth(width).setHeight(height).area();
        double expectedValue = 9;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct square values passed as Rectangle, double assignment")
    void correctValuesAsRectDoubleAssignment() {
        double width = 3;
        double height = 3;

        double actualValue = new Rectangle().setWidth(width).setHeight(height).area();
        double expectedValue = 9;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
