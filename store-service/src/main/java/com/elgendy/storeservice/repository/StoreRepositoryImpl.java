package com.elgendy.storeservice.repository;

import com.elgendy.storeservice.model.Store;
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
public class StoreRepositoryImpl implements StoreRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "itemsListCache", unless= "#result.size() == 0")
    public List<Store> findAll() {
        List<Store> items = null;
        try{
            items = em.createQuery("From Store", Store.class).getResultList();
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    @Cacheable(value = "itemCache", unless="#result == null")
    public Store findById(Integer id) {
        Store itemById = null;
        try{
            itemById = em.find(Store.class, id);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return itemById;
    }

    @Override
    @CachePut(value= "itemCache")
    public void save(Store item) {
        try{
            em.persist(item);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "itemCache")
    public void update(Store item) {
        try{
            em.merge(item);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "itemCache")
    public void delete(Store item) {
        try{
            em.remove(item);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
