package com.elgendy.photoservice.repository;

import com.elgendy.photoservice.model.Photo;
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
public class PhotoRepositoryImpl implements PhotoRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "photosListCache", unless= "#result.size() == 0")
    public List<Photo> findAll() {
        List<Photo> photos = null;
        try{
            photos = em.createQuery("From Photo", Photo.class).getResultList();
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return photos;
    }

    @Override
    @Cacheable(value = "photoCache", unless="#result == null")
    public Photo findById(Integer id) {
        Photo photoById = null;
        try{
            photoById = em.find(Photo.class, id);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return photoById;
    }

    @Override
    @CachePut(value= "photoCache")
    public void save(Photo photo) {
        try{
            em.persist(photo);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "photoCache")
    public void update(Photo photo) {
        try{
            em.merge(photo);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "photoCache")
    public void delete(Photo photo) {
        try{
            em.remove(photo);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
