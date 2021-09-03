package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.client.GetReservationInfoClient;
import com.elgendy.invitationservice.client.GetUserInfoClient;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.repository.InvitationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {

	private final InvitationRepository invitationRepository;
	private final GetUserInfoClient getUserInfoClient;
	private final GetReservationInfoClient getReservationInfoClient;
    
	@Override
	public List<InvitationDTO> getInvitations() {
		List<Invitation> invitations = null;
		List<InvitationDTO> invitationDTOs = null;
		invitations = invitationRepository.findAll();
		invitationDTOs = invitations.stream().map(invitation -> {
			InvitationDTO dto = new InvitationDTO();
			dto.setId(invitation.getId());
			dto.setName(invitation.getName());
			dto.setDate(invitation.getDate());
			dto.setExpiryDate(invitation.getExpiryDate());
			dto.setReservationDTO(getReservationInfoClient.getReservationDTO(invitation)); //call reservation-service
			dto.setUserDTO(getUserInfoClient.getUserDTO(invitation));                      //call user-service
			return dto;
		}).collect(Collectors.toList());
		return invitationDTOs;
	}

	@Override
	public InvitationDTO getInvitation(Integer id) {
		Invitation invitation = null;
		InvitationDTO dto = null;
		invitation = invitationRepository.findById(id);
		if (invitation == null) {
			return null;
		}
		dto = new InvitationDTO();
		dto.setId(invitation.getId());
		dto.setName(invitation.getName());
		dto.setDate(invitation.getDate());
		dto.setExpiryDate(invitation.getExpiryDate());
		dto.setReservationId(invitation.getReservationId());
		dto.setUserId(invitation.getUserId());
		return dto;

	}

	@Override
	public void addInvitation(InvitationDTO invitationDTO) {
		Invitation invitation = new Invitation();
		invitation.setName(invitationDTO.getName());
		invitation.setDate(invitationDTO.getDate());
		invitation.setExpiryDate(invitationDTO.getExpiryDate());
		invitation.setReservationId(invitationDTO.getReservationId());
		invitation.setUserId(invitationDTO.getUserId());
		invitationRepository.save(invitation);

	}

	@Override
	public void updateInvitation(InvitationDTO invitationDTO) {
		Invitation invitation = new Invitation();
		invitation.setId(invitationDTO.getId());
		invitation.setName(invitationDTO.getName());
		invitation.setDate(invitationDTO.getDate());
		invitation.setExpiryDate(invitationDTO.getExpiryDate());
		invitation.setReservationId(invitationDTO.getReservationId());
		invitation.setUserId(invitationDTO.getUserId());
		invitationRepository.update(invitation);
	}

	@Override
	public void deleteInvitation(Integer id) {
		Invitation deletedInvitation = invitationRepository.findById(id);
		invitationRepository.delete(deletedInvitation);
	}

}
