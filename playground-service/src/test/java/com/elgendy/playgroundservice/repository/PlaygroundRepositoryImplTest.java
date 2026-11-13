package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PlaygroundRepositoryImpl.class)
class PlaygroundRepositoryImplTest {

    @Autowired
    private PlaygroundRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(playground("Central Arena", "Downtown"));
        repository.save(playground("North Court", "Downtown"));
        repository.save(playground("Beach Field", "Seaside"));
    }

    @Test
    void findAllReturnsEveryRow() {
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    void searchFiltersByAreaIgnoringCase() {
        List<Playground> result = repository.search("downtown", null, 0, 10);

        assertThat(result).extracting(Playground::getName)
                .containsExactlyInAnyOrder("Central Arena", "North Court");
    }

    @Test
    void searchFiltersByNameAndAreaTogether() {
        List<Playground> result = repository.search("Downtown", "north", 0, 10);

        assertThat(result).extracting(Playground::getName).containsExactly("North Court");
    }

    @Test
    void searchAppliesOffsetAndLimit() {
        List<Playground> result = repository.search(null, null, 1, 1);

        assertThat(result).hasSize(1);
    }

    @Test
    void countMatchesFilters() {
        assertThat(repository.count("Downtown", null)).isEqualTo(2);
        assertThat(repository.count(null, null)).isEqualTo(3);
    }

    @Test
    void updateMergesChanges() {
        Playground existing = repository.search("Seaside", null, 0, 10).get(0);
        existing.setName("Renamed Field");

        Playground updated = repository.update(existing);

        assertThat(updated.getName()).isEqualTo("Renamed Field");
        assertThat(repository.findById(updated.getId()).getName()).isEqualTo("Renamed Field");
    }

    @Test
    void deleteRemovesRow() {
        Playground existing = repository.search("Seaside", null, 0, 10).get(0);

        repository.delete(existing);

        assertThat(repository.findById(existing.getId())).isNull();
    }

    @Test
    void findByIdReturnsNullForUnknownId() {
        assertThat(repository.findById(999_999)).isNull();
    }

    private static Playground playground(String name, String area) {
        Playground playground = new Playground();
        playground.setName(name);
        playground.setArea(area);
        playground.setAddress("1 Main Street");
        return playground;
    }
}
