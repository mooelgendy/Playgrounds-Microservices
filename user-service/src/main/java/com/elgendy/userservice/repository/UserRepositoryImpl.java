package com.elgendy.userservice.repository;

import com.elgendy.userservice.model.User;
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
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "usersListCache", unless= "#result.size() == 0")
    public List<User> findAll() {
        List<User> users = null;
        try{
            users = em.createQuery("From User", User.class).getResultList();
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    @Cacheable(value = "userCache", unless="#result == null")
    public User findById(Integer id) {
        User userById = null;
        try{
            userById = em.find(User.class, id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return userById;
    }

    @Override
    @CachePut(value= "userCache")
    public void save(User user) {
        try{
            em.persist(user);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "userCache")
    public void update(User user) {
        try{
            em.merge(user);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "userCache")
    public void delete(User user) {
        try{
            em.remove(user);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
