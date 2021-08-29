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
		List<Invitation> invitations = null;
        try{
        	invitations = em.createQuery("From Invitation", Invitation.class).getResultList();
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return invitations;	
	}

	@Override
	public Invitation findById(Integer id) {
		Invitation invitationById = null;
        try{
        	invitationById = em.find(Invitation.class, id);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return invitationById;
	}

	@Override
	public void save(Invitation invitation) {
		try{
            em.persist(invitation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
	}

    @Override
	public void update(Invitation invitation) {
		try{
            em.merge(invitation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
	}

	@Override
	public void delete(Invitation invitation) {
		try{
		    em.remove(invitation);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
	}

}
