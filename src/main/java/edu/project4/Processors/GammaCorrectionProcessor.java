package edu.project4.Processors;

import edu.project4.FractalImage;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public final class GammaCorrectionProcessor {
    private GammaCorrectionProcessor() {
    }

    private static final double GAMMA = 3;

    public static void process(@NotNull FractalImage image) {
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

                Color oldColor = pixels[i][j].getColor();
                Color newColor = new Color(
                    (int) (oldColor.getRed() * Math.pow(normal, (1.0 / GAMMA))),
                    (int) (oldColor.getGreen() * Math.pow(normal, (1.0 / GAMMA))),
                    (int) (oldColor.getBlue() * Math.pow(normal, (1.0 / GAMMA)))
                );

                pixels[i][j].setColor(newColor);
            }
        }
    }
}
