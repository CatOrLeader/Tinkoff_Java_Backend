package edu.project4.Transformations;

import edu.project4.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HeartTest {
    @Test
    @DisplayName("Transform")
    void transform() {
        Position position = new Position(1, 1);
        Transformation transformation = new Heart();

        Position actualPosition = transformation.apply(position);
        Position expectedPosition = new Position(
            Math.sqrt(2) * Math.sin(Math.sqrt(2) * (Math.PI / 4.0)),
            -Math.sqrt(2) * Math.cos(Math.sqrt(2) * (Math.PI / 4.0))
        );

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }
}
