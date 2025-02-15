package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.model.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {

	List<InvitationDTO> getInvitations();

	InvitationDTO getInvitation(Long id);

	InvitationDTO addInvitation(InvitationDTO invitationDTO);

	InvitationDTO updateInvitation(InvitationDTO invitationDTO);

	void deleteInvitation(Long id);
}
