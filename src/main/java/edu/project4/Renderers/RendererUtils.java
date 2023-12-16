package edu.project4.Renderers;

import edu.project4.Pixel;
import edu.project4.Position;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

public final class RendererUtils {
    private RendererUtils() {
    }

    public static final int ITERATIONS_TO_SKIP = -20;

    public static final double X_MAX = 1.777;
    public static final double X_MIN = -1.777;
    public static final double Y_MAX = 1;
    public static final double Y_MIN = -1;

    private static final double THRESHOLD = 1;
    private static final double POSSIBLE_DIVERGENCE = 0.05;

    private static final int LOWER_RGB_THRESHOLD = 0;
    private static final int HIGHER_RGB_THRESHOLD = 256;

    public static boolean isInRange(@NotNull Position position) {
        return (X_MIN <= position.x() && position.x() <= X_MAX)
               && (Y_MIN <= position.y() && position.y() <= Y_MAX);
    }

    public static @NotNull Position getRandomPosition(@NotNull Random random) {
        return new Position(random.nextDouble(X_MIN, X_MAX), random.nextDouble(Y_MIN, Y_MAX));
    }

    public static @NotNull AffineTransformation getRandomAffineTransformationFrom(
        @NotNull List<AffineTransformation> transformations
    ) {
        Random random = ThreadLocalRandom.current();
        return transformations.get(
            random.nextInt(transformations.size())
        );
    }

    public static @NotNull List<AffineTransformation> generateAffineTransformations(int count) {
        List<AffineTransformation> transformations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            transformations.add(generateAffineTransformation());
        }
        return transformations;
    }

    private static AffineTransformation generateAffineTransformation() {
        Random random = ThreadLocalRandom.current();

        while (true) {
            double a = random.nextDouble(-THRESHOLD, THRESHOLD);
            double b = random.nextDouble(-THRESHOLD, THRESHOLD);
            double c = random.nextDouble(-THRESHOLD - POSSIBLE_DIVERGENCE, THRESHOLD + POSSIBLE_DIVERGENCE);
            double d = random.nextDouble(-THRESHOLD, THRESHOLD);
            double e = random.nextDouble(-THRESHOLD, THRESHOLD);
            double f = random.nextDouble(-THRESHOLD - POSSIBLE_DIVERGENCE, THRESHOLD + POSSIBLE_DIVERGENCE);
            if (isCorrectCoefficients(a, b, d, e)) {
                return new AffineTransformation(a, b, c, d, e, f, getRandomColor(random));
            }
        }
    }

    private static boolean isCorrectCoefficients(double a, double b, double d, double e) {
        return (Math.pow(a, 2) + Math.pow(d, 2) < 1)
               && (Math.pow(b, 2) + Math.pow(e, 2) < 1)
               &&
               (Math.pow(a, 2) + Math.pow(d, 2) + Math.pow(b, 2) + Math.pow(e, 2) < 1 + Math.pow((a * e - b * d), 2));
    }

    private static Color getRandomColor(Random random) {
        return new Color(
            getRandomRGBConstant(random),
            getRandomRGBConstant(random),
            getRandomRGBConstant(random)
        );
    }

    private static int getRandomRGBConstant(Random random) {
        return random.nextInt(LOWER_RGB_THRESHOLD, HIGHER_RGB_THRESHOLD);
    }

    public static @NotNull Transformation getRandomTransformationFrom(
        @NotNull List<Transformation> transformations
    ) {
        Random random = ThreadLocalRandom.current();
        return transformations.get(
            random.nextInt(transformations.size())
        );
    }

    public static @NotNull Position rotate(@NotNull Position position, double angle) {
        return new Position(
            position.x() * Math.cos(angle) - position.y() * Math.sin(angle),
            position.x() * Math.sin(angle) + position.y() * Math.cos(angle)
        );
    }

    public static void mixColor(@NotNull Pixel pixel, @NotNull AffineTransformation affineTransformation) {
        if (pixel.getHits() == 0) {
            pixel.setColor(affineTransformation.color());
        } else {
            Color pixelColor = pixel.getColor();
            Color affineTransformationColor = affineTransformation.color();
            pixel.setColor(
                new Color(
                    (pixelColor.getRed() + affineTransformationColor.getRed()) / 2,
                    (pixelColor.getGreen() + affineTransformationColor.getGreen()) / 2,
                    (pixelColor.getBlue() + affineTransformationColor.getBlue()) / 2
                )
            );
        }
        pixel.setHits(pixel.getHits() + 1);
    }

    public static double trunc(double num) {
        return num - num % 1;
    }
}
