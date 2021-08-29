package com.elgendy.userservice.repository;

import com.elgendy.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void save(User user) {
        try{
            em.persist(user);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
        try{
            em.merge(user);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(User user) {
        try{
            em.remove(user);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
