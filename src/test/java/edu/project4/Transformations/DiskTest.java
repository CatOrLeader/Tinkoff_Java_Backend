package edu.project4.Transformations;

import edu.project4.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiskTest {
    @Test
    @DisplayName("Transform")
    void transform() {
        Position position = new Position(1, 1);
        Transformation transformation = new Disk();

        Position actualPosition = transformation.apply(position);
        Position expectedPosition = new Position(
            (1.0 / 4.0) * Math.sin(Math.PI * Math.sqrt(2)),
            (1.0 / 4.0) * Math.cos(Math.PI * Math.sqrt(2))
        );

        assertThat(actualPosition).isEqualTo(expectedPosition);
    }
}
