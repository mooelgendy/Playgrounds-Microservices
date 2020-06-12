package com.elgendy.playgroundservice.service;

import com.elgendy.playgroundservice.model.Playground;

import java.util.List;

public interface PlaygroundService {

    List<Playground> getAll();

    Playground getOne(Integer id);

    void add(Playground playground);

    void update(Playground playground);

    void delete(Integer id);
}
