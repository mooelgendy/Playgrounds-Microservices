package com.elgendy.invitationservice.repository;

import com.elgendy.invitationservice.model.Invitation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(value= "invitationsListCache", unless= "#result.size() == 0")
	public List<Invitation> findAll() {
		return em.createQuery("From Invitation", Invitation.class).getResultList();
	}

	@Override
	@Cacheable(value = "invitationCache", unless="#result == null")
	public Invitation findById(Integer id) {
	    return em.find(Invitation.class, id);
	}

	@Override
	@CachePut(value= "InvitationCache")
	public void save(Invitation invitation) {
	    em.persist(invitation);
	}

    @Override
	@CachePut(value = "invitationCache")
	public void update(Invitation invitation) {
	    em.merge(invitation);
	}

	@Override
	@CacheEvict(value= "InvitationCache")
	public void delete(Invitation invitation) {
	    em.remove(invitation);
	}
}
