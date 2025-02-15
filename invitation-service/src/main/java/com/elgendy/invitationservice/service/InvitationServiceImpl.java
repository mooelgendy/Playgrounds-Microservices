package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.client.GetReservationInfoClient;
import com.elgendy.invitationservice.client.GetUserInfoClient;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.repository.InvitationRepository;
import com.elgendy.invitationservice.mapper.InvitationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
@Transactional
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GetUserInfoClient getUserInfoClient;
    private final GetReservationInfoClient getReservationInfoClient;
    private final InvitationMapper invitationMapper;

    @Override
    public List<InvitationDTO> getInvitations() {
        List<Invitation> invitations = invitationRepository.findAll();
        return invitations.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public InvitationDTO getInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        return mapToDTO(invitation);
    }

    @Override
    public InvitationDTO addInvitation(InvitationDTO invitationDTO) {
        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        Invitation savedInvitation = invitationRepository.save(invitation);
        return invitationMapper.toDto(savedInvitation);
    }

    @Override
    public InvitationDTO updateInvitation(InvitationDTO invitationDTO) {
        Invitation invitation = invitationMapper.toEntity(invitationDTO);
        Invitation updatedInvitation = invitationRepository.save(invitation);
        return invitationMapper.toDto(updatedInvitation);
    }

    @Override
    public void deleteInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        invitationRepository.delete(invitation);
    }

    private InvitationDTO mapToDTO(Invitation invitation) {
        InvitationDTO dto = invitationMapper.toDto(invitation);
        dto.setReservationDTO(getReservationInfoClient.getReservationDTO(invitation));
        dto.setUserDTO(getUserInfoClient.getUserDTO(invitation));
        return dto;
    }
}
