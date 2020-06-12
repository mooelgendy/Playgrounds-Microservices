package com.elgendy.playgroundservice.service;

import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.repository.PlaygroundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundServiceImpl implements PlaygroundService {

    private PlaygroundRepository repository;

    @Autowired
    public PlaygroundServiceImpl(PlaygroundRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Playground> getAll() {
        List<Playground> playgroundList = repository.findAll();
        return playgroundList;
    }

    @Override
    public Playground getOne(Integer id) {
        Playground playgroundById = repository.findById(id);
        return playgroundById;
    }

    @Override
    public void add(Playground playground) {
        repository.save(playground);
    }

    @Override
    public void update(Playground playground) {
        repository.update(playground);
    }

    @Override
    public void delete(Integer id) {
        Playground deletedPlayground = repository.findById(id);
        repository.delete(deletedPlayground);
    }
}
