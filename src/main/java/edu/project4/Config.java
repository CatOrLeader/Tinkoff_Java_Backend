package edu.project4;

import org.jetbrains.annotations.Range;

public record Config(@Range(from = 0, to = Long.MAX_VALUE) int samples,
                     @Range(from = 0, to = Short.MAX_VALUE) short iterPerSample,
                     long seed,
                     @Range(from = 0, to = Integer.MAX_VALUE) int affineTransformationsCount,
                     @Range(from = 0, to = Integer.MAX_VALUE) int symmetry) {
}
