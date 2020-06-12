package com.elgendy.storeservice.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.elgendy.storeservice.model.Store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StoreRepositoryImpl implements StoreRepository{

    @PersistenceContext
    private EntityManager em;
    private static Logger LOGGER = LoggerFactory.getLogger(StoreRepositoryImpl.class);

    @Autowired
    public StoreRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Store> findAll() {
        List<Store> items = null;
        try{
            items = em.createQuery("From Store", Store.class).getResultList();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Store findById(Integer id) {
        Store itemById = null;
        try{
            itemById = em.find(Store.class, id);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return itemById;
    }

    @Override
    public void save(Store item) {
        try{
            em.persist(item);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Store item) {
        try{
            em.merge(item);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Store item) {
        try{
            if(em.contains(item)){
                em.remove(item);
            }else {
                throw new RuntimeException("Item You Want To Delete is Not Found!");
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
