package edu.project4.Processors;

import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.Position;
import java.awt.Color;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GammaCorrectionProcessorTest {
    @Test
    @DisplayName("Normalize with respect to one only assigned pixel")
    void normalize_WhenOneOnlyPixelIsNotDefault() {
        FractalImage image = FractalImage.create(500, 500);
        Color commonInitialColor = new Color(16, 32, 64);
        image.setPixel(1, 1, new Pixel(
            new Position(1, 1),
            commonInitialColor,
            10
        ));
        Arrays.stream(image.pixels()).forEach(pixelRow -> Arrays.stream(pixelRow).forEach(
            pixel -> {
                pixel.setNormal(1);
                pixel.setColor(commonInitialColor);
            }
        ));
        Color commonColor = new Color(
            (int) (16 * Math.pow(1, (1.0 / 2.2))),
            (int) (32 * Math.pow(1, (1.0 / 2.2))),
            (int) (64 * Math.pow(1, (1.0 / 2.2)))
        );

        GammaCorrectionProcessor.process(image);

        for (Pixel[] pixels : image.pixels()) {
            for (Pixel pixel : pixels) {
                assertThat(pixel.getColor()).isEqualTo(commonColor);
            }
        }
    }
}
