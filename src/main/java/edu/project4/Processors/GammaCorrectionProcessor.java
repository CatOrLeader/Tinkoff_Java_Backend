package edu.project4.Processors;

import edu.project4.FractalImage;
import edu.project4.RGBConstants;
import org.jetbrains.annotations.NotNull;

public final class GammaCorrectionProcessor {
    private GammaCorrectionProcessor() {
    }

    public static void process(@NotNull FractalImage image) {
        final double gamma = 2.2;
        double max = 0.0;

        var pixels = image.pixels();
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                int hits = pixels[i][j].getHits();
                if (hits == 0) {
                    continue;
                }

                pixels[i][j].setNormal(Math.log10(hits));

                if (pixels[i][j].getNormal() > max) {
                    max = pixels[i][j].getNormal();
                }
            }
        }

        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                double normal = pixels[i][j].getNormal() / max;
                pixels[i][j].setNormal(normal);

                RGBConstants oldConstants = pixels[i][j].getRgb();
                RGBConstants newConstants = new RGBConstants(
                    (int) (oldConstants.r() * Math.pow(normal, (1.0 / gamma))),
                    (int) (oldConstants.g() * Math.pow(normal, (1.0 / gamma))),
                    (int) (oldConstants.b() * Math.pow(normal, (1.0 / gamma)))
                );

                pixels[i][j].setRgb(newConstants);
            }
        }
    }
}
