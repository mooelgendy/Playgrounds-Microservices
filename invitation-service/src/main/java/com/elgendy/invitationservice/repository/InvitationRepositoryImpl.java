package com.elgendy.invitationservice.repository;

import com.elgendy.invitationservice.model.Invitation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

	@PersistenceContext
    private final EntityManager em;

	@Override
	public List<Invitation> findAll() {
		return em.createQuery("From Invitation", Invitation.class).getResultList();
	}

	@Override
	public Invitation findById(Integer id) {
	    return em.find(Invitation.class, id);
	}

	@Override
	public void save(Invitation invitation) {
	    em.persist(invitation);
	}

    @Override
	public void update(Invitation invitation) {
	    em.merge(invitation);
	}

	@Override
	public void delete(Invitation invitation) {
	    em.remove(invitation);
	}
}
