package edu.project4.Transformations;

import edu.project4.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SinusoidalTest {
    @Test
    @DisplayName("Transform")
    void transform() {
        Position position = new Position(Math.PI / 2.0, Math.PI / 2.0);
        Transformation transformation = new Sinusoidal();

        Position actualPosition = transformation.apply(position);
        Position expectedPosition = new Position(
            1, 1
        );

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }
}
