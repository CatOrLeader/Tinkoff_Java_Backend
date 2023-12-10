package edu.project4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.jetbrains.annotations.NotNull;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(@NotNull FractalImage img, @NotNull Path dest, @NotNull ImageFormat format) {
        BufferedImage image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < img.height(); i++) {
            for (int j = 0; j < img.width(); j++) {
                Pixel pixel = Objects.requireNonNull(img.pixel(j, i));
                image.setRGB(j, i, pixel.getRgb().toRgb());
            }
        }

        try {
            ImageIO.write(image, format.name().toLowerCase(), dest.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
