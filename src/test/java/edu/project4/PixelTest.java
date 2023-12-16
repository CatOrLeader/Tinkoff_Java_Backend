package edu.project4;

import java.awt.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PixelTest {
    @Test
    @DisplayName("Get default pixel")
    void getDefaultPixel() {
        Position position = new Position(10, 10);

        Pixel actualPixel = Pixel.getDefault(position);
        Pixel expectedPixel = new Pixel(
            position,
            Color.BLACK,
            0
        );

        assertThat(actualPixel).isEqualTo(expectedPixel);
    }

    @Test
    @DisplayName("Equal pixels")
    void equalPixels() {
        Pixel pixel1 = Pixel.getDefault(new Position(1, 1));
        Pixel pixel2 = new Pixel(
            new Position(0, 0),
            Color.BLACK,
            0
        );
        pixel2.setPosition(pixel1.getPosition());

        assertThat(pixel1).isEqualTo(pixel2);
    }
}
