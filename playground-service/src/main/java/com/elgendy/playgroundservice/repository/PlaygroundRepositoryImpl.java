package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlaygroundRepositoryImpl implements PlaygroundRepository {

    @PersistenceContext
    private EntityManager em;
    private static Logger LOGGER = LoggerFactory.getLogger(PlaygroundRepositoryImpl.class);

    @Autowired
    public PlaygroundRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Playground> findAll() {
        List<Playground> playgrounds = null;
        try{
            playgrounds = em.createQuery("From Playground", Playground.class).getResultList();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return playgrounds;
    }

    @Override
    public Playground findById(Integer id) {
        Playground playgroundById = null;
        try{
            playgroundById = em.find(Playground.class, id);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return playgroundById;
    }

    @Override
    public void save(Playground playground) {
        try{
            em.persist(playground);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Playground playground) {
        try{
            em.merge(playground);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Playground playground) {
        try{
            if(em.contains(playground)){
                em.remove(playground);
            }else {
                throw new RuntimeException("Playground You Want To Delete is Not Found!");
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
