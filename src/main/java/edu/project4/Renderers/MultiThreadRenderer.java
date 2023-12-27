package edu.project4.Renderers;

import edu.project4.Config;
import edu.project4.FractalImage;
import edu.project4.Position;
import edu.project4.Processors.GammaCorrectionProcessor;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Transformation;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import static edu.project4.Renderers.RendererUtils.ITERATIONS_TO_SKIP;
import static edu.project4.Renderers.RendererUtils.X_MAX;
import static edu.project4.Renderers.RendererUtils.X_MIN;
import static edu.project4.Renderers.RendererUtils.Y_MAX;
import static edu.project4.Renderers.RendererUtils.Y_MIN;
import static edu.project4.Renderers.RendererUtils.getRandomAffineTransformationFrom;
import static edu.project4.Renderers.RendererUtils.getRandomPosition;
import static edu.project4.Renderers.RendererUtils.getRandomTransformationFrom;
import static edu.project4.Renderers.RendererUtils.isInRange;
import static edu.project4.Renderers.RendererUtils.mixColor;
import static edu.project4.Renderers.RendererUtils.rotate;
import static edu.project4.Renderers.RendererUtils.trunc;

public final class MultiThreadRenderer implements Renderer {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int POSSIBLE_THREADS_NUMBER = AVAILABLE_PROCESSORS * 2;

    @Override
    public void render(
        @NotNull FractalImage image,
        @NotNull List<Transformation> transformations,
        @NotNull Config config
    ) {
        List<AffineTransformation> affineTransformations = Collections.unmodifiableList(
            RendererUtils.generateAffineTransformations(config.affineTransformationsCount())
        );

        try (ExecutorService executor = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS)) {
            final int commonSampleSize = config.samples() / POSSIBLE_THREADS_NUMBER;

            for (int i = 0; i < POSSIBLE_THREADS_NUMBER; i++) {
                if (i == POSSIBLE_THREADS_NUMBER - 1) {
                    executor.execute(new FractalImageRendererPerSample(
                        image,
                        affineTransformations,
                        transformations,
                        commonSampleSize + config.samples() % POSSIBLE_THREADS_NUMBER,
                        config.iterPerSample(),
                        config.symmetry(),
                        config.seed()
                    ));
                    break;
                }

                executor.execute(new FractalImageRendererPerSample(
                    image,
                    affineTransformations,
                    transformations,
                    commonSampleSize,
                    config.iterPerSample(),
                    config.symmetry(),
                    config.seed()
                ));
            }

            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Error while waiting the executor to shutdown");
            }
        }

        GammaCorrectionProcessor.process(image);
    }

    private record FractalImageRendererPerSample(FractalImage image,
                                                 List<AffineTransformation> affineTransformations,
                                                 List<Transformation> transformations, int sample, short iterations,
                                                 int symmetry, long seed)
        implements Runnable {

        @Override
        public void run() {
            final ThreadLocal<Random> threadLocalContainer = ThreadLocal.withInitial(Random::new);
            final Random random = threadLocalContainer.get();
            random.setSeed(seed);

            for (int i = 0; i < sample; i++) {
                Position newPosition = getRandomPosition(random);

                for (int j = ITERATIONS_TO_SKIP; j < iterations; j++) {
                    AffineTransformation currentAffineTransformation =
                        getRandomAffineTransformationFrom(affineTransformations);
                    Transformation currentTransformation = getRandomTransformationFrom(transformations);

                    newPosition = currentAffineTransformation.apply(newPosition);
                    newPosition = currentTransformation.apply(newPosition);

                    if (j < 0) {
                        continue;
                    }

                    double theta2 = 0.0;
                    for (int step = 0; step < symmetry; theta2 += Math.PI * 2 / symmetry, step++) {
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
        }
    }
}
