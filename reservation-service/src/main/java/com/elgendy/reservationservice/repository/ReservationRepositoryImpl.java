package com.elgendy.reservationservice.repository;

import com.elgendy.reservationservice.model.Reservation;
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
public class ReservationRepositoryImpl implements ReservationRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Cacheable(value= "reservationsListCache", unless= "#result.size() == 0")
    public List<Reservation> findAll() {
        List<Reservation> reservations = null;
        try{
            reservations = em.createQuery("From Reservation", Reservation.class).getResultList();
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return reservations;
    }

    @Override
    @Cacheable(value = "reservationCache", unless="#result == null")
    public Reservation findById(Integer id) {
        Reservation reservationById = null;
        try{
            reservationById = em.find(Reservation.class, id);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return reservationById;
    }

    @Override
    @CachePut(value= "reservationCache")
    public void save(Reservation reservation) {
        try{
            em.persist(reservation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CachePut(value= "reservationCache")
    public void update(Reservation reservation) {
        try{
            em.merge(reservation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value= "reservationCache")
    public void delete(Reservation reservation) {
        try{
            em.remove(reservation);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
