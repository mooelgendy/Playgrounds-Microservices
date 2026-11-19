package com.elgendy.playgroundservice.mapper;

import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PlaygroundDTO;
import org.springframework.stereotype.Component;

/**
 * Converts between the persistence model and the API model.
 *
 * <p>The controller used to repeat this mapping inline for every endpoint,
 * which made it easy for the create/update/read representations to drift
 * apart. Centralizing it keeps the translation in one place.</p>
 */
@Component
public class PlaygroundMapper {

    public Playground toEntity(PlaygroundDTO dto) {
        Playground playground = new Playground();
        copyToEntity(dto, playground);
        return playground;
    }

    /**
     * Copies the mutable API fields onto an existing entity. The identifier is
     * intentionally not copied so an update cannot move a row to another id.
     */
    public void copyToEntity(PlaygroundDTO dto, Playground playground) {
        playground.setName(trim(dto.getName()));
        playground.setAddress(trim(dto.getAddress()));
        playground.setDescription(trim(dto.getDescription()));
        playground.setPhone(trim(dto.getPhone()));
        playground.setArea(trim(dto.getArea()));
        playground.setAvailableTime(dto.getAvailableTime());
        playground.setPricePerHour(trim(dto.getPricePerHour()));
    }

    public PlaygroundDTO toDto(Playground playground) {
        PlaygroundDTO dto = new PlaygroundDTO();
        dto.setId(playground.getId());
        dto.setName(playground.getName());
        dto.setAddress(playground.getAddress());
        dto.setDescription(playground.getDescription());
        dto.setPhone(playground.getPhone());
        dto.setArea(playground.getArea());
        dto.setAvailableTime(playground.getAvailableTime());
        dto.setPricePerHour(playground.getPricePerHour());
        return dto;
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
