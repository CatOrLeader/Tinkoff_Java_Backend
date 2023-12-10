package edu.project4;

import edu.project4.Renderers.SingleThreadRenderer;
import edu.project4.Transformations.Disk;
import edu.project4.Transformations.Heart;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FractalImage image = FractalImage.create(1920, 1080);
        Rect domain = new Rect(100, 100, 800, 800);
        var transformations = List.of(
            new Heart(),
            new Disk()
        );
        Config config = new Config(1_000, (short) 128, 0,
            5, 50);

        new SingleThreadRenderer().render(image, domain, transformations, config);

        Path dir = Paths.get(System.getProperty("user.dir"), "fractal_output.png");
        ImageUtils.save(image, dir, ImageFormat.PNG);
    }
}
