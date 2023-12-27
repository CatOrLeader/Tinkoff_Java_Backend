package edu.project4.Transformations;

import edu.project4.Position;
import java.awt.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AffineTransformationTest {
    @Test
    @DisplayName("Transform")
    void transform() {
        Position position = new Position(1, 1);
        AffineTransformation transformation = new AffineTransformation(
            1, 1, 1, 1, 1, 1, Color.RED
        );

        Position actualValue = transformation.apply(position);
        Position expectedValue = new Position(3, 3);

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
