package dev.playgrounds.ailab;

/**
 * Synthetic low-quality class used only to exercise SketchPR AI review behavior.
 */
public class SqlConcat39 {
    public String findUser(java.sql.Connection connection, String name) throws Exception {
        String sql = "SELECT * FROM users WHERE name = '" + name + "'";
        return connection.createStatement().executeQuery(sql).toString();
    }
}
