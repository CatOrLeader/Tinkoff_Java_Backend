package edu.project4.Renderers;

import edu.project4.Config;
import edu.project4.FractalImage;
import edu.project4.Rect;
import edu.project4.Transformations.Transformation;
import java.util.List;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Renderer {
    void render(
        @NotNull FractalImage image, @NotNull Rect domain, @NotNull List<Transformation> transformations,
        @NotNull Config config
    );
}
