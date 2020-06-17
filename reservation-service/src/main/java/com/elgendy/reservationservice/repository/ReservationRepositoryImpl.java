package com.elgendy.reservationservice.repository;

import com.elgendy.reservationservice.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    @PersistenceContext
    private EntityManager em;
    private static Logger LOGGER = LoggerFactory.getLogger(ReservationRepositoryImpl.class);

    @Autowired
    public ReservationRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = null;
        try{
            reservations = em.createQuery("From Reservation", Reservation.class).getResultList();
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return reservations;
    }

    @Override
    public Reservation findById(Integer id) {
        Reservation reservationById = null;
        try{
            reservationById = em.find(Reservation.class, id);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return reservationById;
    }

    @Override
    public void save(Reservation reservation) {
        try{
            em.persist(reservation);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(Reservation reservation) {
        try{
            em.merge(reservation);
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Reservation reservation) {
        try{
            em.remove(reservation);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
