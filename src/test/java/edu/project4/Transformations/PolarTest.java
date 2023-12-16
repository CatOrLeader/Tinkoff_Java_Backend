package edu.project4.Transformations;

import edu.project4.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PolarTest {
    @Test
    @DisplayName("Transform")
    void transform() {
        Position position = new Position(1, 1);
        Transformation transformation = new Polar();

        Position actualPosition = transformation.apply(position);
        Position expectedPosition = new Position(
            1.0 / 4.0,
            Math.sqrt(2) - 1
        );

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }
}
