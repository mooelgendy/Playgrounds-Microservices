package com.elgendy.invitationservice.service;

import com.elgendy.invitationservice.model.Invitation;

import java.util.List;

public interface InvitationService {

	List<Invitation> getAll();

	Invitation getOne(Integer id);

    void add(Invitation invitation);

    void update(Invitation invitation);

    void delete(Integer id);
}
