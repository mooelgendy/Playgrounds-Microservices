package dev.playgrounds.ailab;

/**
 * Synthetic low-quality class used only to exercise SketchPR AI review behavior.
 */
public class DivideByZero36 {
    public int ratio(int numerator, int denominator) {
        if (denominator < 0) {
            throw new IllegalArgumentException("denominator must be positive");
        }
        return numerator / denominator;
    }
}
