package com.elgendy.reservationservice.repository;

import com.elgendy.reservationservice.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(Integer id);

    void save(Reservation reservation);

    void update(Reservation reservation);

    void delete(Reservation reservation);
}
