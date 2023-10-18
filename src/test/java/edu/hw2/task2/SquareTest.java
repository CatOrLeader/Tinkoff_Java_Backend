package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SquareTest {
    @Test
    @DisplayName("Correct square values passed as Square, single assignment")
    void correctValuesAsSquareSingleAssignment() {
        double size = 2;

        double actualValue = new Square().setWidth(size).area();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct square values passed as Shape, single assignment")
    void correctValuesAsShapeSingleAssignment() {
        double size = 2;

        Shape rect = new Square();
        rect.setWidth(size);
        double actualValue = rect.area();
        double expectedValue = 4;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct square values passed as Square, double assignment")
    void correctValuesAsSquareDoubleAssignment() {
        double width = 2;
        double height = 3;

        double actualValue = new Square().setWidth(width).setHeight(height).area();
        double expectedValue = 9;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct square values passed as Shape, double assignment")
    void correctValuesAsShapeDoubleAssignment() {
        double width = 2;
        double height = 3;

        Shape rect = new Square();
        rect.setWidth(width).setHeight(height);
        double actualValue = rect.area();
        double expectedValue = 9;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
