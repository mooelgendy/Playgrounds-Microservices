package com.elgendy.playgroundservice.service;

import com.elgendy.playgroundservice.exception.ApiNotFoundException;
import com.elgendy.playgroundservice.exception.InvalidRequestException;
import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PageResponse;
import com.elgendy.playgroundservice.model.dto.PlaygroundSearchCriteria;
import com.elgendy.playgroundservice.repository.PlaygroundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaygroundServiceImplTest {

    @Mock
    private PlaygroundRepository repository;

    @InjectMocks
    private PlaygroundServiceImpl service;

    @Test
    void getAllDelegatesToRepository() {
        List<Playground> playgrounds = Collections.singletonList(playground(1));
        when(repository.findAll()).thenReturn(playgrounds);

        assertThat(service.getAll()).isSameAs(playgrounds);
    }

    @Test
    void getOneReturnsPlayground() {
        Playground playground = playground(2);
        when(repository.findById(2)).thenReturn(playground);

        assertThat(service.getOne(2)).isSameAs(playground);
    }

    @Test
    void getOneThrowsWhenMissing() {
        when(repository.findById(99)).thenReturn(null);

        assertThatThrownBy(() -> service.getOne(99))
                .isInstanceOf(ApiNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void searchBuildsPageEnvelopeAndOffset() {
        PlaygroundSearchCriteria criteria = new PlaygroundSearchCriteria();
        criteria.setArea("Downtown");
        criteria.setName("Arena");
        criteria.setPage(1);
        criteria.setSize(2);

        List<Playground> content = Collections.singletonList(playground(3));
        when(repository.search("Downtown", "Arena", 2, 2)).thenReturn(content);
        when(repository.count("Downtown", "Arena")).thenReturn(5L);

        PageResponse<Playground> page = service.search(criteria);

        assertThat(page.getContent()).isSameAs(content);
        assertThat(page.getPage()).isEqualTo(1);
        assertThat(page.getSize()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.isFirst()).isFalse();
        assertThat(page.isLast()).isFalse();
    }

    @Test
    void searchClampsInvalidPagingValues() {
        PlaygroundSearchCriteria criteria = new PlaygroundSearchCriteria();
        criteria.setPage(-4);
        criteria.setSize(0);

        when(repository.search(null, null, 0, 1)).thenReturn(Collections.emptyList());
        when(repository.count(null, null)).thenReturn(0L);

        PageResponse<Playground> page = service.search(criteria);

        assertThat(page.getPage()).isZero();
        assertThat(page.getSize()).isEqualTo(1);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.isLast()).isTrue();
    }

    @Test
    void addReturnsSavedEntity() {
        Playground playground = playground(null);
        when(repository.save(playground)).thenReturn(playground);

        assertThat(service.add(playground)).isSameAs(playground);
        verify(repository).save(playground);
    }

    @Test
    void updateRequiresId() {
        Playground playground = playground(null);

        assertThatThrownBy(() -> service.update(playground))
                .isInstanceOf(InvalidRequestException.class);
        verify(repository, never()).update(any(Playground.class));
    }

    @Test
    void updateThrowsWhenRowIsMissing() {
        Playground playground = playground(12);
        when(repository.findById(12)).thenReturn(null);

        assertThatThrownBy(() -> service.update(playground))
                .isInstanceOf(ApiNotFoundException.class);
        verify(repository, never()).update(any(Playground.class));
    }

    @Test
    void updateMergesExistingRow() {
        Playground playground = playground(12);
        when(repository.findById(12)).thenReturn(playground(12));
        when(repository.update(playground)).thenReturn(playground);

        assertThat(service.update(playground)).isSameAs(playground);
    }

    @Test
    void deleteThrowsWhenRowIsMissing() {
        when(repository.findById(8)).thenReturn(null);

        assertThatThrownBy(() -> service.delete(8)).isInstanceOf(ApiNotFoundException.class);
        verify(repository, never()).delete(any(Playground.class));
    }

    @Test
    void deleteRemovesExistingRow() {
        Playground playground = playground(8);
        when(repository.findById(8)).thenReturn(playground);

        service.delete(8);

        verify(repository).delete(playground);
    }

    private static Playground playground(Integer id) {
        Playground playground = new Playground();
        playground.setId(id);
        playground.setName("Arena " + id);
        playground.setAddress("1 Main Street");
        playground.setArea("Downtown");
        return playground;
    }
}
