package com.elgendy.invitationservice.controller;

import com.elgendy.invitationservice.config.TestConfig;
import com.elgendy.invitationservice.config.TestRedisConfig;
import com.elgendy.invitationservice.config.TestSecurityConfig;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.elgendy.invitationservice.repository.InvitationRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class, TestConfig.class, TestRedisConfig.class})
class InvitationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private InvitationRepository invitationRepository;

    @BeforeEach
    void setUp() {
        invitationRepository.deleteAll();
    }

    @Test
    void shouldGetAllInvitations() throws Exception {
        // Create a test invitation
        InvitationDTO dto = new InvitationDTO();
        dto.setName("Test Invitation");

        mockMvc.perform(post("/api/invitations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Get all invitations
        mockMvc.perform(get("/api/invitations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value("Test Invitation"));
    }

    @Test
    void shouldCreateInvitation() throws Exception {
        InvitationDTO dto = new InvitationDTO();
        dto.setName("Invitation 1");
        
        mockMvc.perform(post("/api/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Invitation 1"));
    }

    @Test
    void shouldGetInvitation() throws Exception {
        // First create an invitation
        InvitationDTO dto = new InvitationDTO();
        dto.setName("Invitation 1");
        
        String response = mockMvc.perform(post("/api/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        InvitationDTO createdInvitation = objectMapper.readValue(response, InvitationDTO.class);

        // Then get it by ID
        mockMvc.perform(get("/api/invitations/" + createdInvitation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdInvitation.getId()))
                .andExpect(jsonPath("$.name").value("Invitation 1"));
    }

    @Test
    void shouldUpdateInvitation() throws Exception {
        // First create an invitation
        InvitationDTO dto = new InvitationDTO();
        dto.setName("Invitation 1");

        String response = mockMvc.perform(post("/api/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        InvitationDTO createdInvitation = objectMapper.readValue(response, InvitationDTO.class);

        // Update the invitation
        createdInvitation.setName("Invitation 2");
        
        mockMvc.perform(put("/api/invitations/" + createdInvitation.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdInvitation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Invitation 2"));
    }

    @Test
    void shouldDeleteInvitation() throws Exception {
        // First create an invitation
        InvitationDTO dto = new InvitationDTO();
        dto.setName("Test Invitation");
        
        String response = mockMvc.perform(post("/api/invitations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        InvitationDTO createdInvitation = objectMapper.readValue(response, InvitationDTO.class);

        // Delete the invitation
        mockMvc.perform(delete("/api/invitations/" + createdInvitation.getId()))
                .andExpect(status().isNoContent());
    }
} 