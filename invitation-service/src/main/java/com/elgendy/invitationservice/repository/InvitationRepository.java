package com.elgendy.invitationservice.repository;

import com.elgendy.invitationservice.model.Invitation;

import java.util.List;

public interface InvitationRepository {

    List<Invitation> findAll();

	Invitation findById(Integer id);

    void save(Invitation invitation);

    void update(Invitation invitation);

    void delete(Invitation invitation);
}
