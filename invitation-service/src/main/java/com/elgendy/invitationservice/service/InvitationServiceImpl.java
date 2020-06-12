package com.elgendy.invitationservice.service;

import java.util.List;

import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl implements InvitationService {

	private InvitationRepository repository;

    @Autowired
    public InvitationServiceImpl(InvitationRepository repository) {
        this.repository = repository;
    }
    
	@Override
	public List<Invitation> getAll() {
		List<Invitation> invitationList = repository.findAll();
        return invitationList;
	}

	@Override
	public Invitation getOne(Integer id) {
		Invitation invitationById = repository.findById(id);
        return invitationById;
	}

	@Override
	public void add(Invitation invitation) {
		repository.save(invitation);
	}

	@Override
	public void update(Invitation invitation) {
		repository.update(invitation);
	}

	@Override
	public void delete(Integer id) {
		Invitation deletedInvitation = repository.findById(id);
        repository.delete(deletedInvitation);
	}

}
