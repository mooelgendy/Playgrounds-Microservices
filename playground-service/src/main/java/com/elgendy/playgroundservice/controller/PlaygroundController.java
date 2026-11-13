package com.elgendy.playgroundservice.controller;

import com.elgendy.playgroundservice.exception.ApiNotFoundException;
import com.elgendy.playgroundservice.mapper.PlaygroundMapper;
import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PageResponse;
import com.elgendy.playgroundservice.model.dto.PlaygroundDTO;
import com.elgendy.playgroundservice.model.dto.PlaygroundSearchCriteria;
import com.elgendy.playgroundservice.service.PlaygroundService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HTTP layer for playgrounds.
 *
 * <p>This class is intentionally thin: it validates input, delegates to the
 * service, and maps entities to DTOs. Error handling is centralized in
 * {@link com.elgendy.playgroundservice.exception.ApiExceptionHandler}.</p>
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/playground")
public class PlaygroundController {

    private final PlaygroundService service;
    private final PlaygroundMapper mapper;

    @GetMapping({"", "/"})
    public List<PlaygroundDTO> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public PageResponse<PlaygroundDTO> search(@Valid @ModelAttribute PlaygroundSearchCriteria criteria) {
        PageResponse<Playground> page = service.search(criteria);
        List<PlaygroundDTO> content = page.getContent().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return PageResponse.of(content, page.getPage(), page.getSize(), page.getTotalElements());
    }

    @GetMapping("/{id}")
    public PlaygroundDTO findOne(@PathVariable("id") @Min(1) Integer id) {
        Playground playground = service.getOne(id);
        if (playground == null) {
            throw new ApiNotFoundException("Playground " + id + " was not found");
        }
        return mapper.toDto(playground);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PlaygroundDTO> create(@Valid @RequestBody PlaygroundDTO dto) {
        Playground created = service.add(mapper.toEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toDto(created));
    }

    @PutMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public PlaygroundDTO update(@Valid @RequestBody PlaygroundDTO dto) {
        Playground playground = mapper.toEntity(dto);
        playground.setId(dto.getId());
        return mapper.toDto(service.update(playground));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") @Min(1) Integer id) {
        service.delete(id);
    }
}
