package edu.project4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.NotNull;

public record FractalImage(Pixel[][] pixels, int width, int height) {
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = LOCK.readLock();
    private static final Lock WRITE_LOCK = LOCK.writeLock();

    @NotNull
    public static FractalImage create(int width, int height) {
        FractalImage image = new FractalImage(new Pixel[height][width], width, height);
        fill(image);
        return image;
    }

    public boolean contains(int x, int y) {
        return (0 <= x && x < width) && (0 <= y && y < height);
    }

    public boolean contains(@NotNull Position position) {
        return (0 <= position.x() && position.x() < width) && (0 <= position.y() && position.y() < height);
    }

    @NotNull
    public Pixel pixel(@NotNull Position position) {
        READ_LOCK.lock();
        try {
            if (!contains(position)) {
                throw new IndexOutOfBoundsException();
            }

            return pixels[(int) position.y()][(int) position.x()];
        } finally {
            READ_LOCK.unlock();
        }
    }

    @NotNull
    public Pixel pixel(int x, int y) {
        READ_LOCK.lock();
        try {
            if (!contains(x, y)) {
                throw new IndexOutOfBoundsException();
            }

            return pixels[y][x];
        } finally {
            READ_LOCK.unlock();
        }
    }

    public void setPixel(@NotNull Position position, @NotNull Pixel pixel) {
        WRITE_LOCK.lock();
        try {
            if (!contains(position)) {
                throw new IndexOutOfBoundsException();
            }

            pixels[(int) position.y()][(int) position.x()] = pixel;
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    public void setPixel(int x, int y, @NotNull Pixel pixel) {
        WRITE_LOCK.lock();
        try {
            if (!contains(x, y)) {
                throw new IndexOutOfBoundsException();
            }

            pixels[y][x] = pixel;
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    private static void fill(FractalImage image) {
        var pixels = image.pixels;
        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                pixels[y][x] = Pixel.getDefault(new Position(x, y));
            }
        }
    }
}
