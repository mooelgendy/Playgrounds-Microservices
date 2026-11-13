package com.elgendy.playgroundservice.service;

import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PageResponse;
import com.elgendy.playgroundservice.model.dto.PlaygroundSearchCriteria;

import java.util.List;

public interface PlaygroundService {

    List<Playground> getAll();

    PageResponse<Playground> search(PlaygroundSearchCriteria criteria);

    /**
     * @throws com.elgendy.playgroundservice.exception.ApiNotFoundException when no row has the given id
     */
    Playground getOne(Integer id);

    Playground add(Playground playground);

    Playground update(Playground playground);

    void delete(Integer id);
}
