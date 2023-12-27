package edu.project4.Renderers;

import edu.project4.Pixel;
import edu.project4.Position;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Disk;
import edu.project4.Transformations.Polar;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project4.Renderers.RendererUtils.getRandomPosition;
import static edu.project4.Renderers.RendererUtils.isInRange;
import static edu.project4.Renderers.RendererUtils.trunc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RendererUtilsTest {
    @Test
    @DisplayName("Points in/out of the range")
    void pointsInOutOfTheRange() {
        Position inRange = new Position(0, 0);
        Position outRange = new Position(-2, 1.77);

        assertThat(isInRange(inRange)).isTrue();
        assertThat(isInRange(outRange)).isFalse();
    }

    @Test
    @DisplayName("Get random positions, all in correct range")
    void getPositionsInCorrectRange() {

        for (int i = 0; i < 10_000; i++) {
            assertThat(isInRange(getRandomPosition(new Random()))).isTrue();
        }
    }

    @Test
    @DisplayName("Get random transformations from the lists")
    void randomTransformationsFromTheList() {
        List<AffineTransformation> emptyAffineTransformationsList = new ArrayList<>();
        List<AffineTransformation> affineTransformations = List.of(
            new AffineTransformation(1, 1, 1, 1, 1, 1, Color.BLACK),
            new AffineTransformation(1, 1, 1, 1, 1, 1, Color.YELLOW)
        );

        List<Transformation> emptyTransformationsList = new ArrayList<>();
        List<Transformation> transformations = List.of(
            new Disk(),
            new Polar()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> RendererUtils.getRandomAffineTransformationFrom(emptyAffineTransformationsList));
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> RendererUtils.getRandomTransformationFrom(emptyTransformationsList));

        assertThat(RendererUtils.getRandomAffineTransformationFrom(affineTransformations)).isIn(affineTransformations);
        assertThat(RendererUtils.getRandomTransformationFrom(transformations)).isIn(transformations);
    }

    @Test
    @DisplayName("Generate random affine transformations")
    void generateRandomAffineTransformations() {
        List<AffineTransformation> affineTransformations = RendererUtils.generateAffineTransformations(10);

        assertThat(affineTransformations).hasSize(10);
        for (var transformation : affineTransformations) {
            assertThat(transformation).isNotNull();
        }
    }

    @Test
    @DisplayName("Rotating points")
    void rotatingPoints() {
        Position zero = new Position(0, 0);
        Position one = new Position(1, 1);
        double angle = Math.PI / 4;

        assertThat(RendererUtils.rotate(zero, angle)).isEqualTo(zero);
        assertThat(RendererUtils.rotate(one, angle)).isEqualTo(
            new Position(Math.cos(angle) - Math.sin(angle), Math.cos(angle) + Math.sin(angle))
        );
    }

    @Test
    @DisplayName("Trunc some numbers")
    void truncNumbers() {
        double withoutDecimalPart = 1;
        double withDecimalPart = 1.5;

        assertThat(trunc(withoutDecimalPart)).isEqualTo(withoutDecimalPart);
        assertThat(trunc(withDecimalPart)).isEqualTo(withoutDecimalPart);
    }

    @Test
    @DisplayName("Mix color")
    void mixColor() {
        Pixel withoutHits = Pixel.getDefault(new Position(0, 0));
        Pixel withHits = Pixel.getDefault(new Position(1, 1));
        withHits.setHits(1);
        AffineTransformation affineTransformation = new AffineTransformation(1, 1, 1, 1, 1, 1,
            new Color(10, 10, 10));
        RendererUtils.mixColor(withoutHits, affineTransformation);
        RendererUtils.mixColor(withHits, affineTransformation);

        assertThat(withoutHits.getColor()).isEqualTo(affineTransformation.color());
        assertThat(withHits.getColor()).isEqualTo(new Color(5, 5, 5));
    }
}
