package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {
    @Test
    @DisplayName("Addition of two points")
    void additionTwoPoints() {
        Position point1 = new Position(1, 1);
        Position point2 = new Position(2, 2);

        Position actualValue = point1.addition(point2);
        Position expectedValue = new Position(3, 3);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Subtraction of two points")
    void subtractionTwoPoints() {
        Position point1 = new Position(1, 1);
        Position point2 = new Position(2, 2);

        Position actualValue = point1.subtraction(point2);
        Position expectedValue = new Position(-1, -1);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("String representation test")
    void stringRepresentation() {
        Position point1 = new Position(1, 1);

        String actualValue = point1.toString();
        String expectedValue = "Position: x=1; y=1";

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
