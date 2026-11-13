package com.elgendy.playgroundservice.controller;

import com.elgendy.playgroundservice.exception.ApiNotFoundException;
import com.elgendy.playgroundservice.exception.InvalidRequestException;
import com.elgendy.playgroundservice.mapper.PlaygroundMapper;
import com.elgendy.playgroundservice.model.Playground;
import com.elgendy.playgroundservice.model.dto.PageResponse;
import com.elgendy.playgroundservice.model.dto.PlaygroundDTO;
import com.elgendy.playgroundservice.service.PlaygroundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaygroundController.class)
@Import(PlaygroundMapper.class)
class PlaygroundControllerTest {

    private static final String VALID_BODY = "{\"name\":\"Central Arena\",\"address\":\"1 Main Street\","
            + "\"area\":\"Downtown\",\"phone\":\"01000000000\",\"pricePerHour\":\"150.00\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaygroundService service;

    @Test
    void getAllReturnsMappedList() throws Exception {
        when(service.getAll()).thenReturn(Collections.singletonList(playground(1)));

        mockMvc.perform(get("/api/playground/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Central Arena"));
    }

    @Test
    void findOneReturnsMappedPlayground() throws Exception {
        when(service.getOne(1)).thenReturn(playground(1));

        mockMvc.perform(get("/api/playground/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Central Arena"));
    }

    @Test
    void findOneReturns404WhenMissing() throws Exception {
        when(service.getOne(99)).thenThrow(new ApiNotFoundException("Playground 99 was not found"));

        mockMvc.perform(get("/api/playground/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Playground 99 was not found"));
    }

    @Test
    void findOneRejectsNonPositiveId() throws Exception {
        mockMvc.perform(get("/api/playground/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
        verify(service, never()).getOne(anyInt());
    }

    @Test
    void createReturnsCreatedWithLocationHeader() throws Exception {
        when(service.add(any(Playground.class))).thenAnswer(invocation -> {
            Playground entity = invocation.getArgument(0);
            entity.setId(7);
            return entity;
        });

        mockMvc.perform(post("/api/playground/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/playground/7")))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.address").value("1 Main Street"));
    }

    @Test
    void createRejectsBlankName() throws Exception {
        mockMvc.perform(post("/api/playground/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"address\":\"1 Main Street\",\"area\":\"Downtown\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors[*].field", hasItem("name")));
        verify(service, never()).add(any(Playground.class));
    }

    @Test
    void createRejectsMalformedJson() throws Exception {
        mockMvc.perform(post("/api/playground/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed request"));
    }

    @Test
    void updateReturnsMappedPlayground() throws Exception {
        when(service.update(any(Playground.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/api/playground/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":5,\"name\":\"Central Arena\",\"address\":\"1 Main Street\",\"area\":\"Downtown\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.area").value("Downtown"));
    }

    @Test
    void updateWithoutIdReturns400() throws Exception {
        when(service.update(any(Playground.class))).thenThrow(new InvalidRequestException("id is required"));

        mockMvc.perform(put("/api/playground/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Central Arena\",\"address\":\"1 Main Street\",\"area\":\"Downtown\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("id is required"));
    }

    @Test
    void deleteReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/playground/3"))
                .andExpect(status().isOk());

        verify(service).delete(3);
    }

    @Test
    void searchRejectsNegativePage() throws Exception {
        mockMvc.perform(get("/api/playground/search").param("page", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[*].field", hasItem("page")));
    }

    @Test
    void searchRejectsOversizedPageSize() throws Exception {
        mockMvc.perform(get("/api/playground/search").param("size", "500"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[*].field", hasItem("size")));
    }

    @Test
    void searchReturnsPaginatedContent() throws Exception {
        PageResponse<Playground> page = PageResponse.of(Collections.singletonList(playground(1)), 0, 5, 1);
        when(service.search(any())).thenReturn(page);

        mockMvc.perform(get("/api/playground/search").param("size", "5").param("area", "Downtown"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Central Arena"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    private static Playground playground(Integer id) {
        Playground playground = new Playground();
        playground.setId(id);
        playground.setName("Central Arena");
        playground.setAddress("1 Main Street");
        playground.setArea("Downtown");
        playground.setPhone("01000000000");
        playground.setPricePerHour("150.00");
        return playground;
    }
}
