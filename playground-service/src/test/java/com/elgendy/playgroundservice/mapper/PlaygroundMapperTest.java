package com.elgendy.playgroundservice.mapper;

import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PlaygroundDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class PlaygroundMapperTest {

    private final PlaygroundMapper mapper = new PlaygroundMapper();

    @Test
    void toEntityTrimsTextFields() {
        PlaygroundDTO dto = new PlaygroundDTO();
        dto.setName("  Central Arena  ");
        dto.setAddress("  1 Main Street ");
        dto.setArea(" Downtown ");
        dto.setDescription("  Indoor courts  ");
        dto.setPhone(" 0100 000 0000 ");
        dto.setPricePerHour(" 150.00 ");

        Playground entity = mapper.toEntity(dto);

        assertThat(entity.getName()).isEqualTo("Central Arena");
        assertThat(entity.getAddress()).isEqualTo("1 Main Street");
        assertThat(entity.getArea()).isEqualTo("Downtown");
        assertThat(entity.getDescription()).isEqualTo("Indoor courts");
        assertThat(entity.getPhone()).isEqualTo("0100 000 0000");
        assertThat(entity.getPricePerHour()).isEqualTo("150.00");
    }

    @Test
    void toEntityDoesNotCopyId() {
        PlaygroundDTO dto = new PlaygroundDTO();
        dto.setId(42);
        dto.setName("Central Arena");

        Playground entity = mapper.toEntity(dto);

        assertThat(entity.getId()).isNull();
    }

    @Test
    void toDtoMapsEveryField() {
        Date availableTime = new Date();
        Playground entity = new Playground();
        entity.setId(3);
        entity.setName("Central Arena");
        entity.setAddress("1 Main Street");
        entity.setArea("Downtown");
        entity.setDescription("Indoor courts");
        entity.setPhone("0100 000 0000");
        entity.setAvailableTime(availableTime);
        entity.setPricePerHour("150.00");

        PlaygroundDTO dto = mapper.toDto(entity);

        assertThat(dto.getId()).isEqualTo(3);
        assertThat(dto.getName()).isEqualTo("Central Arena");
        assertThat(dto.getAddress()).isEqualTo("1 Main Street");
        assertThat(dto.getArea()).isEqualTo("Downtown");
        assertThat(dto.getDescription()).isEqualTo("Indoor courts");
        assertThat(dto.getPhone()).isEqualTo("0100 000 0000");
        assertThat(dto.getAvailableTime()).isEqualTo(availableTime);
        assertThat(dto.getPricePerHour()).isEqualTo("150.00");
    }
}
