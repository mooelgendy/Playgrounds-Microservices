package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.client.GetReservationInfoClient;
import com.elgendy.invitationservice.client.GetUserInfoClient;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.model.dto.ReservationDTO;
import com.elgendy.invitationservice.model.dto.UserDTO;
import com.elgendy.invitationservice.repository.InvitationRepository;
import com.elgendy.invitationservice.mapper.InvitationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private GetUserInfoClient getUserInfoClient;

    @Mock
    private GetReservationInfoClient getReservationInfoClient;

    @Mock
    private InvitationMapper invitationMapper;

    @InjectMocks
    private InvitationServiceImpl invitationService;

    private Invitation invitation;
    private InvitationDTO invitationDTO;
    private UserDTO userDTO;
    private ReservationDTO reservationDTO;

    @BeforeEach
    void setUp() {
        invitation = new Invitation();
        invitation.setId(1L);

        invitationDTO = new InvitationDTO();
        invitationDTO.setId(1L);

        userDTO = new UserDTO();
        userDTO.setId(1L);

        reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
    }

    @Test
    void getInvitations_ShouldReturnListOfInvitationDTOs() {
        // Arrange
        List<Invitation> invitations = Collections.singletonList(invitation);
        when(invitationRepository.findAll()).thenReturn(invitations);
        when(invitationMapper.toDto(any(Invitation.class))).thenReturn(invitationDTO);
        when(getUserInfoClient.getUserDTO(any(Invitation.class))).thenReturn(userDTO);
        when(getReservationInfoClient.getReservationDTO(any(Invitation.class))).thenReturn(reservationDTO);

        // Act
        List<InvitationDTO> result = invitationService.getInvitations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(invitationDTO.getId(), result.get(0).getId());
        verify(invitationRepository).findAll();
    }

    @Test
    void getInvitation_WithValidId_ShouldReturnInvitationDTO() {
        // Arrange
        when(invitationRepository.findById(1L)).thenReturn(Optional.of(invitation));
        when(invitationMapper.toDto(any(Invitation.class))).thenReturn(invitationDTO);
        when(getUserInfoClient.getUserDTO(any(Invitation.class))).thenReturn(userDTO);
        when(getReservationInfoClient.getReservationDTO(any(Invitation.class))).thenReturn(reservationDTO);

        // Act
        InvitationDTO result = invitationService.getInvitation(1L);

        // Assert
        assertNotNull(result);
        assertEquals(invitationDTO.getId(), result.getId());
        verify(invitationRepository).findById(1L);
    }

    @Test
    void getInvitation_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(invitationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> invitationService.getInvitation(1L));
        verify(invitationRepository).findById(1L);
    }

    @Test
    void addInvitation_ShouldReturnSavedInvitationDTO() {
        // Arrange
        when(invitationMapper.toEntity(any(InvitationDTO.class))).thenReturn(invitation);
        when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);
        when(invitationMapper.toDto(any(Invitation.class))).thenReturn(invitationDTO);

        // Act
        InvitationDTO result = invitationService.addInvitation(invitationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invitationDTO.getId(), result.getId());
        verify(invitationRepository).save(any(Invitation.class));
    }

    @Test
    void updateInvitation_ShouldReturnUpdatedInvitationDTO() {
        // Arrange
        when(invitationMapper.toEntity(any(InvitationDTO.class))).thenReturn(invitation);
        when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);
        when(invitationMapper.toDto(any(Invitation.class))).thenReturn(invitationDTO);

        // Act
        InvitationDTO result = invitationService.updateInvitation(invitationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invitationDTO.getId(), result.getId());
        verify(invitationRepository).save(any(Invitation.class));
    }

    @Test
    void deleteInvitation_WithValidId_ShouldDeleteInvitation() {
        // Arrange
        when(invitationRepository.findById(1L)).thenReturn(Optional.of(invitation));

        // Act
        invitationService.deleteInvitation(1L);

        // Assert
        verify(invitationRepository).delete(invitation);
    }

    @Test
    void deleteInvitation_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(invitationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> invitationService.deleteInvitation(1L));
        verify(invitationRepository, never()).delete(any());
    }
} 