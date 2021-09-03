package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.model.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {

	List<InvitationDTO> getInvitations();

	InvitationDTO getInvitation(Integer id);

    void addInvitation(InvitationDTO invitationDTO);

    void updateInvitation(InvitationDTO invitationDTO);

    void deleteInvitation(Integer id);
}
