package edu.project4.Renderers;

import edu.project4.Config;
import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.Position;
import edu.project4.RGBConstants;
import edu.project4.Rect;
import edu.project4.Transformations.AffineTransformation;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.jetbrains.annotations.NotNull;
import static edu.project4.Renderers.RendererUtils.X_MAX;
import static edu.project4.Renderers.RendererUtils.X_MIN;
import static edu.project4.Renderers.RendererUtils.Y_MAX;
import static edu.project4.Renderers.RendererUtils.Y_MIN;
import static edu.project4.Renderers.RendererUtils.getRandomPosition;
import static edu.project4.Renderers.RendererUtils.rotate;

public final class SingleThreadRenderer implements Renderer {
    @Override
    public void render(
        @NotNull FractalImage image,
        @NotNull Rect domain,
        @NotNull List<Transformation> transformations,
        @NotNull Config config
    ) {
        Random random = new Random(config.seed());
        List<AffineTransformation> affineTransformations =
            RendererUtils.generateAffineTransformations(config.affineTransformationsCount());

        for (int i = 0; i < config.samples(); i++) {
            Position newPosition = getRandomPosition(domain, random);

            for (int j = 0; j < config.iterPerSample(); j++) {
                AffineTransformation affine =
                    RendererUtils.getRandomAffineTransformationFrom(affineTransformations, random);
                newPosition = affine.apply(newPosition);

                Transformation transformation =
                    RendererUtils.getRandomTransformationFrom(transformations, random);
                newPosition = transformation.apply(newPosition);

                double theta2 = 0.0;
                for (int s = 0; s < config.symmetry(); theta2 += Math.PI * 2 / config.symmetry(), s++) {
                    if (s <= 20) {
                        continue;
                    }
                    var pointRotated = rotate(newPosition, theta2);
                    if (!domain.contains(pointRotated)) {
                        continue;
                    }

                    System.out.println(pointRotated.getX() + " " + pointRotated.getY());

                    int x1 = (int) (image.width() - (((X_MAX - pointRotated.getX()) / (X_MAX - X_MIN)) * image.width()));
                    int y1 =
                        (int) (image.height() - (((Y_MAX - pointRotated.getY()) / (Y_MAX - Y_MIN)) * image.height()));

                    if (!image.contains(x1, y1)) {
                        continue;
                    }

                    System.out.println(x1 + " " + y1);

                    Pixel currentPixel = Objects.requireNonNull(image.pixel(x1, y1));
                    if (currentPixel.getHits() == 0) {
                        image.pixels()[y1][x1] = new Pixel(
                            new Position(x1, y1),
                            affine.rgb(),
                            1, 0
                        );
                    } else {
                        image.pixels()[y1][x1] = new Pixel(
                            new Position(x1, y1),
                            new RGBConstants(
                                (currentPixel.getRgb().r() + affine.rgb().r()) / 2,
                                (currentPixel.getRgb().g() + affine.rgb().g()) / 2,
                                (currentPixel.getRgb().b() + affine.rgb().b()) / 2
                            ),
                            currentPixel.getHits() + 1, 0
                        );
                    }
                }
            }
        }

//        GammaCorrectionProcessor.process(image);
    }
}
