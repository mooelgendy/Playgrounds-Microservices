package com.elgendy.reservationservice.controller;

import com.elgendy.reservationservice.exception.InternalServerErrorException;
import com.elgendy.reservationservice.model.Reservation;
import com.elgendy.reservationservice.model.dto.ReservationDTO;
import com.elgendy.reservationservice.service.PlaygroundInfo;
import com.elgendy.reservationservice.service.ReservationService;
import com.elgendy.reservationservice.service.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController implements Serializable {

    private final ReservationService service;
    private final UserInfo userInfo;
    private final PlaygroundInfo playgroundInfo;

    @GetMapping("/")
    public List<ReservationDTO> getAll(){
        List<Reservation> reservations;
        List<ReservationDTO> reservationDTOs;
        try{
            reservations = service.getAll();
            reservationDTOs = reservations.stream().map(reservation -> {
                ReservationDTO dto = new ReservationDTO();
                dto.setId(reservation.getId());
                dto.setName(reservation.getName());
                dto.setHoursNumber(reservation.getHoursNumber());
                dto.setPlayersNeeded(reservation.getPlayersNeeded());
                dto.setReservedTime(reservation.getReservedTime());
                dto.setPlaygroundDTO(playgroundInfo.getPlaygroundDTO(reservation));  // call playground-service
                dto.setUserDTO(userInfo.getUserDTO(reservation));                    // call user-service
                return dto;
            }).collect(Collectors.toList());
            return reservationDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    public ReservationDTO findOne(@PathVariable("id") Integer id){
        Reservation reservation;
        ReservationDTO dto;
        try{
            reservation = service.getOne(id);
            if(reservation == null){
                return null;
            }
            dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setName(reservation.getName());
            dto.setHoursNumber(reservation.getHoursNumber());
            dto.setPlayersNeeded(reservation.getPlayersNeeded());
            dto.setReservedTime(reservation.getReservedTime());
            dto.setUserId(reservation.getUserId());
            dto.setPlaygroundId(reservation.getPlaygroundId());
            return dto;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ReservationDTO dto) {
        Reservation reservation;
        try{
            reservation = new Reservation();
            reservation.setName(dto.getName());
            reservation.setHoursNumber(dto.getHoursNumber());
            reservation.setPlayersNeeded(dto.getPlayersNeeded());
            reservation.setReservedTime(dto.getReservedTime());
            reservation.setPlaygroundId(dto.getPlaygroundId());
            reservation.setUserId(dto.getUserId());
            service.add(reservation);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody ReservationDTO dto) {
        Reservation reservation;
        try{
            reservation = new Reservation();
            reservation.setName(dto.getName());
            reservation.setHoursNumber(dto.getHoursNumber());
            reservation.setPlayersNeeded(dto.getPlayersNeeded());
            reservation.setReservedTime(dto.getReservedTime());
            reservation.setUserId(dto.getUserId());
            reservation.setPlaygroundId(dto.getPlaygroundId());
            service.update(reservation);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }
}
