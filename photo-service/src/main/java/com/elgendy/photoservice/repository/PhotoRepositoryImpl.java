package com.elgendy.photoservice.repository;

import com.elgendy.photoservice.model.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    @PersistenceContext
    private EntityManager em;
    private static Logger LOGGER = LoggerFactory.getLogger(PhotoRepositoryImpl.class);

    @Autowired
    public PhotoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Photo> findAll() {
        List<Photo> photos = null;
        try{
            photos = em.createQuery("From Photo", Photo.class).getResultList();
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return photos;
    }

    @Override
    public Photo findById(Integer id) {
        Photo photoById = null;
        try{
            photoById = em.find(Photo.class, id);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return photoById;
    }

    @Override
    public void save(Photo photo) {
        try{
            em.persist(photo);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Photo photo) {
        try{
            em.merge(photo);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Photo photo) {
        try{
            if(em.contains(photo)){
                em.remove(photo);
            }else {
                throw new RuntimeException("photo You Want To Delete is Not Found!");
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
