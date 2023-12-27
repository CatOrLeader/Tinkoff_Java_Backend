package edu.project4.Renderers;

import edu.project4.Config;
import edu.project4.FractalImage;
import edu.project4.Position;
import edu.project4.Processors.GammaCorrectionProcessor;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;
import static edu.project4.Renderers.RendererUtils.ITERATIONS_TO_SKIP;
import static edu.project4.Renderers.RendererUtils.X_MAX;
import static edu.project4.Renderers.RendererUtils.X_MIN;
import static edu.project4.Renderers.RendererUtils.Y_MAX;
import static edu.project4.Renderers.RendererUtils.Y_MIN;
import static edu.project4.Renderers.RendererUtils.isInRange;
import static edu.project4.Renderers.RendererUtils.mixColor;
import static edu.project4.Renderers.RendererUtils.rotate;
import static edu.project4.Renderers.RendererUtils.trunc;

public final class SingleThreadRenderer implements Renderer {
    @Override
    public void render(
        @NotNull FractalImage image,
        @NotNull List<Transformation> transformations,
        @NotNull Config config
    ) {
        Random random = ThreadLocalRandom.from(new Random(config.seed()));

        List<AffineTransformation> affineTransformations =
            RendererUtils.generateAffineTransformations(config.affineTransformationsCount());

        for (int i = 0; i < config.samples(); i++) {
            Position newPosition = RendererUtils.getRandomPosition(random);

            for (int j = ITERATIONS_TO_SKIP; j < config.iterPerSample(); j++) {
                AffineTransformation currentAffineTransformation =
                    RendererUtils.getRandomAffineTransformationFrom(affineTransformations);
                Transformation currentTransformation = RendererUtils.getRandomTransformationFrom(transformations);

                newPosition = currentAffineTransformation.apply(newPosition);
                newPosition = currentTransformation.apply(newPosition);

                if (j < 0) {
                    continue;
                }

                double theta2 = 0.0;
                for (int step = 0; step < config.symmetry(); theta2 += Math.PI * 2 / config.symmetry(), step++) {
                    newPosition = rotate(newPosition, theta2);

                    if (isInRange(newPosition)) {
                        newPosition = new Position(
                            image.width() - trunc(((X_MAX - newPosition.x()) / (X_MAX - X_MIN)) * image.width()),
                            image.height() - trunc(((Y_MAX - newPosition.y()) / (Y_MAX - Y_MIN)) * image.height())
                        );

                        if (!image.contains(newPosition)) {
                            continue;
                        }

                        mixColor(image.pixel(newPosition), currentAffineTransformation);
                    }
                }
            }
        }

        GammaCorrectionProcessor.process(image);
    }
}
