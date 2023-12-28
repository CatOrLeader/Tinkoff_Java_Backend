package edu.project4.Renderers;

import edu.project4.Config;
import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.Transformations.Disk;
import edu.project4.Transformations.Polar;
import edu.project4.Transformations.Transformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.awt.Color;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class RendererTest {
    static Arguments[] renderers() {
        return new Arguments[] {
            Arguments.of(new SingleThreadRenderer()),
            Arguments.of(new MultiThreadRenderer())
        };
    }

    private static boolean isBlank(FractalImage image) {
        int count = 0;
        for (Pixel[] row : image.pixels()) {
            for (Pixel pixel : row) {
                if (!pixel.getColor().equals(Color.BLACK)) count++;
            }
        }
        return (image.width() * image.height()) / 2 < count;
    }

    @ParameterizedTest
    @MethodSource("renderers")
    @DisplayName("Not black image")
    void notBlackImage(Renderer renderer) {
        final int width = 250, height = 250;
        FractalImage image = FractalImage.create(width, height);
        List<Transformation> transformationList = List.of(new Disk(), new Polar());
        Config config = new Config(1_000, (short) 1_000, System.nanoTime(), 5, 20);
        renderer.render(image, transformationList, config);

        assertThat(isBlank(image)).isFalse();
    }
}
