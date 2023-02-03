package com.elgendy.teamservice.repository;

import com.elgendy.teamservice.model.Team;
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
public class TeamRepositoryImpl implements TeamRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "teamsListCache", unless= "#result.size() == 0")
    public List<Team> findAll() {
        List<Team> teams = null;
        try{
            teams = em.createQuery("From Team", Team.class).getResultList();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return teams;
    }

    @Override
    @Cacheable(value = "teamCache", unless="#result == null")
    public Team findById(Integer id) {
        Team teamById = null;
        try{
            teamById = em.find(Team.class, id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return teamById;
    }

    @Override
    @CachePut(value= "teamCache")
    public void save(Team team) {
        try{
            em.persist(team);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "teamCache")
    public void update(Team team) {
        try{
            em.merge(team);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "teamCache")
    public void delete(Team team) {
        try{
            em.remove(team);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
