package com.elgendy.reservationservice.service;

import com.elgendy.reservationservice.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getAll();

    Reservation getOne(Integer id);

    void add(Reservation reservation);

    void update(Reservation reservation);

    void delete(Integer id);
}
