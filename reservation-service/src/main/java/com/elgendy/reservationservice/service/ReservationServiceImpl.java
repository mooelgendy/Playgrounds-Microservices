package com.elgendy.reservationservice.service;

import com.elgendy.reservationservice.model.Reservation;
import com.elgendy.reservationservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository repository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservationList = repository.findAll();
        return reservationList;
    }

    @Override
    public Reservation getOne(Integer id) {
        Reservation reservationById = repository.findById(id);
        return reservationById;
    }

    @Override
    public void add(Reservation reservation) {
        repository.save(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        repository.update(reservation);
    }

    @Override
    public void delete(Integer id) {
        Reservation deletedReservation = repository.findById(id);
        repository.delete(deletedReservation);
    }
}