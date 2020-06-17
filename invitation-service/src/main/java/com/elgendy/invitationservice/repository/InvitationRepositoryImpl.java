package com.elgendy.invitationservice.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.elgendy.invitationservice.model.Invitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvitationRepositoryImpl implements InvitationRepository{

	
	@PersistenceContext
    private EntityManager em;
    private static Logger LOGGER = LoggerFactory.getLogger(InvitationRepositoryImpl.class);

    @Autowired
    public InvitationRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    
	@Override
	public List<Invitation> findAll() {
		List<Invitation> invitations = null;
        try{
        	invitations = em.createQuery("From Invitation", Invitation.class).getResultList();
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return invitations;	
	}

	@Override
	public Invitation findById(Integer id) {
		Invitation invitationById = null;
        try{
        	invitationById = em.find(Invitation.class, id);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return invitationById;
	}

	@Override
	public void save(Invitation invitation) {
		try{
            em.persist(invitation);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
	}

//    @Override
//    public Integer saveAndFlush(Invitation invitation) {
//        return null;
//    }

    @Override
	public void update(Invitation invitation) {
		try{
            em.merge(invitation);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
	}

	@Override
	public void delete(Invitation invitation) {
		try{
		    em.remove(invitation);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
	}

}
