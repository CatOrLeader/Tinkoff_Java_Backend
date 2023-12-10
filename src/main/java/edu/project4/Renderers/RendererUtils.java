package edu.project4.Renderers;

import edu.project4.Position;
import edu.project4.RGBConstants;
import edu.project4.Rect;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Transformation;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RendererUtils {
    private RendererUtils() {
    }

    public static final double X_MAX = 1.777;
    public static final double X_MIN = -1.777;
    public static final double Y_MAX = 1;
    public static final double Y_MIN = -1;

    private static final double THRESHOLD = 1.5;
    private static final double POSSIBLE_DIVERGENCE = 2;

    public static @NotNull Position getRandomPosition(@NotNull Rect domain, @NotNull Random random) {
        double x = random.nextDouble(domain.x(), domain.x() + domain.width());
        double y = random.nextDouble(domain.y(), domain.y() + domain.height());

        return new Position(x, y);
    }

    public static @NotNull AffineTransformation getRandomAffineTransformationFrom(
        @NotNull List<AffineTransformation> transformations,
        @NotNull Random random
    ) {
        return transformations.get(
            random.nextInt(transformations.size())
        );
    }

    public static @NotNull Transformation getRandomTransformationFrom(
        @NotNull List<Transformation> transformations,
        @NotNull Random random
    ) {
        return transformations.get(
            random.nextInt(transformations.size())
        );
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

            if (
                (Math.pow(a, 2) + Math.pow(d, 2) < THRESHOLD)
                && (Math.pow(b, 2) + Math.pow(e, 2) < THRESHOLD)
                &&
                (Math.pow(a, 2) + Math.pow(d, 2) + Math.pow(b, 2) + Math.pow(e, 2) < 1 + Math.pow((a * e - b * d), 2))
            ) {
                RGBConstants rgb = new RGBConstants(
                    getRandomRGBConstant(random),
                    getRandomRGBConstant(random),
                    getRandomRGBConstant(random)
                );
                return new AffineTransformation(a, b, c, d, e, f, rgb);
            }
        }
    }

    private static int getRandomRGBConstant(Random random) {
        return random.nextInt(RGBConstants.LOWER_BOUND, RGBConstants.UPPER_BOUND);
    }

    public static @NotNull List<AffineTransformation> generateAffineTransformations(int count) {
        List<AffineTransformation> transformations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            transformations.add(generateAffineTransformation());
        }
        return transformations;
    }

    public static @NotNull Position rotate(@NotNull Position position, double angle) {
        return new Position(
            position.getX() * Math.cos(angle) - position.getY() * Math.sin(angle),
            position.getX() * Math.sin(angle) + position.getY() * Math.cos(angle)
        );
    }
}
