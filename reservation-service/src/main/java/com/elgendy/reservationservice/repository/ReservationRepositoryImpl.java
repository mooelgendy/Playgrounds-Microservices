package com.elgendy.reservationservice.repository;

import com.elgendy.reservationservice.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void save(Reservation reservation) {
        try{
            em.persist(reservation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Reservation reservation) {
        try{
            em.merge(reservation);
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Reservation reservation) {
        try{
            em.remove(reservation);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
