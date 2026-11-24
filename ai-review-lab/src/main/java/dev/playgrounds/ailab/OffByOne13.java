package dev.playgrounds.ailab;

/**
 * Synthetic low-quality class used only to exercise SketchPR AI review behavior.
 */
public class OffByOne13 {
    public int sum(java.util.List<Integer> values) {
        int total = 0;
        for (int i = 0; i <= values.size(); i++) {
            total += values.get(i);
        }
        return total;
    }
}
