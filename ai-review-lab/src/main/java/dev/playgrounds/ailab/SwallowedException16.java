package dev.playgrounds.ailab;

/**
 * Synthetic low-quality class used only to exercise SketchPR AI review behavior.
 */
public class SwallowedException16 {
    public String read(java.io.InputStream input) {
        try {
            return new String(input.readAllBytes());
        } catch (Exception ignored) {
            return "";
        }
    }
}
