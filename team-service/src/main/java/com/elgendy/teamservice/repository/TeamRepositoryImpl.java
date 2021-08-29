package com.elgendy.teamservice.repository;

import com.elgendy.teamservice.model.Team;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void save(Team team) {
        try{
            em.persist(team);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Team team) {
        try{
            em.merge(team);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Team team) {
        try{
            em.remove(team);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
