package com.elgendy.playgroundservice.service;

import com.elgendy.playgroundservice.exception.ApiNotFoundException;
import com.elgendy.playgroundservice.exception.InvalidRequestException;
import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PageResponse;
import com.elgendy.playgroundservice.model.dto.PlaygroundSearchCriteria;
import com.elgendy.playgroundservice.repository.PlaygroundRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for playgrounds.
 *
 * <p>Missing rows now surface as {@link ApiNotFoundException} instead of being
 * returned as {@code null}; the controller used to serialize that as an empty
 * {@code 200} body, which made a missing playground indistinguishable from a
 * successful request.</p>
 */
@AllArgsConstructor
@Service
public class PlaygroundServiceImpl implements PlaygroundService {

    private static final String CACHE_NAME = "playgroundCache";

    private final PlaygroundRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Playground> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Playground> search(PlaygroundSearchCriteria criteria) {
        int page = Math.max(criteria.getPage(), 0);
        int size = Math.min(Math.max(criteria.getSize(), 1), PlaygroundSearchCriteria.MAX_PAGE_SIZE);
        int offset = page * size;

        List<Playground> content = repository.search(criteria.getArea(), criteria.getName(), offset, size);
        long total = repository.count(criteria.getArea(), criteria.getName());
        return PageResponse.of(content, page, size, total);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "#id")
    public Playground getOne(Integer id) {
        Playground playground = repository.findById(id);
        if (playground == null) {
            throw new ApiNotFoundException("Playground " + id + " was not found");
        }
        return playground;
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Playground add(Playground playground) {
        return repository.save(playground);
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Playground update(Playground playground) {
        if (playground.getId() == null) {
            throw new InvalidRequestException("id is required when updating a playground");
        }
        if (repository.findById(playground.getId()) == null) {
            throw new ApiNotFoundException("Playground " + playground.getId() + " was not found");
        }
        return repository.update(playground);
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Integer id) {
        Playground playground = repository.findById(id);
        if (playground == null) {
            throw new ApiNotFoundException("Playground " + id + " was not found");
        }
        repository.delete(playground);
    }
}
