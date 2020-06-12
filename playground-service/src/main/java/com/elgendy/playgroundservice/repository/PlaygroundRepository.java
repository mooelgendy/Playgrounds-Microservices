package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;

import java.util.List;

public interface PlaygroundRepository {

    List<Playground> findAll();

    Playground findById(Integer id);

    void save(Playground playground);

    void update(Playground playground);

    void delete(Playground playground);
}
