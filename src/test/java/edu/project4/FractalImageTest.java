package edu.project4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class FractalImageTest {
    private static final int WIDTH = 1920, HEIGHT = 1080;
    private static FractalImage image;

    private static final Position POSITION_10_10 = new Position(10, 10);
    private static final Pixel PIXEL_10_10 = Pixel.getDefault(POSITION_10_10);

    private static final Position POSITION_0_0 = new Position(0, 0);
    private static final Pixel PIXEL_0_0 = Pixel.getDefault(POSITION_0_0);

    @BeforeEach
    void setup() {
        image = FractalImage.create(WIDTH, HEIGHT);
        image.setPixel(POSITION_0_0, PIXEL_0_0);
        image.setPixel(POSITION_10_10, PIXEL_10_10);
    }

    @Test
    @DisplayName("Contains the position, correct")
    void containsPosition_Correct() {
        assertThat(image.contains(POSITION_0_0)).isTrue();
        assertThat(image.contains(10, 10)).isTrue();
    }

    @Test
    @DisplayName("Contains the position, incorrect")
    void containsPosition_Incorrect() {
        final Position incorrectPosition = new Position(-5, 5);

        assertThat(image.contains(incorrectPosition)).isFalse();
        assertThat(image.contains(5, -5)).isFalse();
    }

    @Test
    @DisplayName("Get the pixel, correct")
    void getPixel_Correct() {
        assertThat(image.pixel(POSITION_0_0)).isEqualTo(PIXEL_0_0);
        assertThat(image.pixel(10, 10)).isEqualTo(PIXEL_10_10);
    }

    @Test
    @DisplayName("Get the pixel, incorrect")
    void getPixel_Incorrect() {
        final Position incorrectPosition = new Position(5, HEIGHT);

        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> image.pixel(incorrectPosition));
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> image.pixel(5, HEIGHT));
    }

    @Test
    @DisplayName("Set the pixel, correct")
    void setPixel_Correct() {
        final Position newPosition = new Position(5, 5);
        final Pixel newPositionPixel = Pixel.getDefault(newPosition);
        final int x = 2, y = 2;
        final Pixel newIntPositionsPixel = Pixel.getDefault(new Position(x, y));

        image.setPixel(newPosition, Pixel.getDefault(newPosition));
        image.setPixel(x, y, Pixel.getDefault(new Position(x, y)));

        assertThat(image.pixel(newPosition)).isEqualTo(newPositionPixel);
        assertThat(image.pixel(x, y)).isEqualTo(newIntPositionsPixel);
    }

    @Test
    @DisplayName("Set the pixel, incorrect")
    void setPixel_Incorrect() {
        final Position newPositionIncorrect = new Position(WIDTH, 5);
        final Pixel newPositionIncorrectPixel = Pixel.getDefault(newPositionIncorrect);
        final int x = 2, y = HEIGHT;
        final Pixel newIntPositionsIncorrectPixel = Pixel.getDefault(new Position(x, y));

        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> image.setPixel(newPositionIncorrect, newPositionIncorrectPixel));
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> image.setPixel(x, y, newIntPositionsIncorrectPixel));
    }
}
