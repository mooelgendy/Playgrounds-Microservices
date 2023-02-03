package com.elgendy.playgroundservice.repository;

import com.elgendy.playgroundservice.model.Playground;
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
public class PlaygroundRepositoryImpl implements PlaygroundRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "playgroundsListCache", unless= "#result.size() == 0")
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
    @Cacheable(value = "playgroundCache", unless="#result == null")
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
    @CachePut(value= "playgroundCache")
    public void save(Playground playground) {
        try{
            em.persist(playground);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "playgroundCache")
    public void update(Playground playground) {
        try{
            em.merge(playground);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "playgroundCache")
    public void delete(Playground playground) {
        try{
            em.remove(playground);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
