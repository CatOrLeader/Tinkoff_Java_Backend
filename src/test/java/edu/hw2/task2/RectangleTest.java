package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class RectangleTest {
    @Test
    @DisplayName("Correct rectangle values passed as Rectangle")
    void correctValuesAsRect() {
        double width = 2;
        double height = 3;

        double actualValue = new Rectangle().setWidth(width).setHeight(height).area();
        double expectedValue = 6;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct rectangle values passed as Shape")
    void correctValuesAsShape() {
        double width = 2;
        double height = 3;

        Shape rect = new Rectangle();
        rect.setWidth(width).setHeight(height);
        double actualValue = rect.area();
        double expectedValue = 6;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct rectangle values passed as Square")
    void correctValuesAsSquare() {
        double width = 2;
        double height = 3;

        Rectangle rect = new Square();
        rect.setWidth(width).setHeight(height);
        double actualValue = rect.area();
        double expectedValue = 6;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
