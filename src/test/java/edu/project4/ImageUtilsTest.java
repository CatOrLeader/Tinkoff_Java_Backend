package edu.project4;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageUtilsTest {
    @TempDir
    Path dir;

    private static final String filename = "fractal.png";

    @Test
    @DisplayName("Create new image file from scratch")
    public void createNewImageFromScratch() {
        final ImageFormat format = ImageFormat.PNG;

        ImageUtils.save(FractalImage.create(1920, 1080), dir.resolve(filename), format);
        assertThat(Arrays.stream(Objects.requireNonNull(dir.toFile().listFiles()))
            .map(File::getAbsolutePath)).containsExactly(
            dir.resolve(filename).toFile().getAbsolutePath()
        );
    }
}
