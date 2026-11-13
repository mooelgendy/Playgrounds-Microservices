package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;

import java.util.List;

public interface PlaygroundRepository {

    List<Playground> findAll();

    /**
     * Runs a filtered, paginated query. Both filters may be {@code null}.
     */
    List<Playground> search(String area, String name, int offset, int limit);

    /**
     * Counts the rows matching the same filters used by {@link #search}.
     */
    long count(String area, String name);

    Playground findById(Integer id);

    Playground save(Playground playground);

    Playground update(Playground playground);

    void delete(Playground playground);
}
