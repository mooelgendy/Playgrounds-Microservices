package com.elgendy.reservationservice.controller;

import com.elgendy.reservationservice.model.dto.PlaygroundDTO;
import com.elgendy.reservationservice.model.dto.ReservationDTO;
import com.elgendy.reservationservice.model.Reservation;
import com.elgendy.reservationservice.model.dto.UserDTO;
import com.elgendy.reservationservice.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
public class ReservationController implements Serializable {

    private ReservationService service;
    private static Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<ReservationDTO> getAll(){
        List<Reservation> reservations = null;
        List<ReservationDTO> reservationDTOs = null;
        try{
            reservations = service.getAll();
            reservationDTOs = reservations.stream().map(reservation -> {
                ReservationDTO dto = new ReservationDTO();
                dto.setId(reservation.getId());
                dto.setName(reservation.getName());
                dto.setHoursNumber(reservation.getHoursNumber());
                dto.setPlayersNeeded(reservation.getPlayersNeeded());
                dto.setReservedTime(reservation.getReservedTime());
                PlaygroundDTO playgroundDTO = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8083/playgrounds/api/playground/" + reservation.getPlaygroundId())
                        .retrieve()
                        .bodyToMono(PlaygroundDTO.class)
                        .block();
                LOGGER.info("PlaygroundDTO: {}", playgroundDTO.toString());
                UserDTO userDTO = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8087/playgrounds/api/user/" + reservation.getUserId())
                        .retrieve()
                        .bodyToMono(UserDTO.class)
                        .block();
                LOGGER.info("UserDTO: {}", userDTO.toString());
                dto.setPlaygroundDTO(playgroundDTO);
                dto.setUserDTO(userDTO);
                return dto;
            }).collect(Collectors.toList());
            return reservationDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public ReservationDTO findOne(@PathVariable("id") Integer id){
        Reservation reservation = null;
        ReservationDTO dto = null;
        try{
            reservation = service.getOne(id);
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
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ReservationDTO dto) {
        Reservation reservation = null;
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
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody ReservationDTO dto) {
        Reservation reservation = null;
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
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }
}
