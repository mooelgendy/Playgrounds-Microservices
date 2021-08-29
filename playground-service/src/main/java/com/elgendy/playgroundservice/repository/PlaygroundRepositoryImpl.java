package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Repository
public class PlaygroundRepositoryImpl implements PlaygroundRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Playground> findAll() {
        List<Playground> playgrounds = null;
        try{
            playgrounds = em.createQuery("From Playground", Playground.class).getResultList();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return playgrounds;
    }

    @Override
    public Playground findById(Integer id) {
        Playground playgroundById = null;
        try{
            playgroundById = em.find(Playground.class, id);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return playgroundById;
    }

    @Override
    public void save(Playground playground) {
        try{
            em.persist(playground);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Playground playground) {
        try{
            em.merge(playground);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Playground playground) {
        try{
            em.remove(playground);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
