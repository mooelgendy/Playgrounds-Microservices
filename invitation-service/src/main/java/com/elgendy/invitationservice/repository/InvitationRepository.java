package com.elgendy.invitationservice.repository;

import com.elgendy.invitationservice.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    
    @Override
    @Cacheable(value = "invitationsListCache", unless = "#result.size() == 0")
    List<Invitation> findAll();

    @Override
    @Cacheable(value = "invitationCache", unless = "#result == null")
    Optional<Invitation> findById(Long id);

    @Override
    @CachePut(value = "invitationCache")
    <S extends Invitation> S save(S invitation);

    @Override
    @CacheEvict(value = "invitationCache")
    void delete(Invitation invitation);
}
