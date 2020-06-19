package com.elgendy.invitationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.elgendy.invitationservice.exception.InternalServerErrorException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.service.InvitationService;
import com.elgendy.invitationservice.service.ReservationInfo;
import com.elgendy.invitationservice.service.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

	private InvitationService service;
    private static Logger LOGGER = LoggerFactory.getLogger(InvitationController.class);

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private ReservationInfo reservationInfo;

    @Autowired
    public InvitationController(InvitationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<InvitationDTO> getAll(){
        List<Invitation> invitations = null;
        List<InvitationDTO> invitationDTOs = null;
        try{
            invitations = service.getAll();
            invitationDTOs = invitations.stream().map(invitation -> {
                        InvitationDTO dto = new InvitationDTO();
                        dto.setId(invitation.getId());
                        dto.setName(invitation.getName());
                        dto.setDate(invitation.getDate());
                        dto.setExpiryDate(invitation.getExpiryDate());
                        dto.setReservationDTO(reservationInfo.getReservationDTO(invitation)); //call reservation-service
                        dto.setUserDTO(userInfo.getUserDTO(invitation));                      //call user-service
                        LOGGER.info(dto.toString());
                        return dto;
            }).collect(Collectors.toList());
            return invitationDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    public InvitationDTO findOne(@PathVariable("id") Integer id){
        Invitation invitation = null;
        InvitationDTO dto = null;
        try{
            invitation = service.getOne(id);
            if(invitation == null){
                return null;
            }
            dto = new InvitationDTO();
            dto.setId(invitation.getId());
            dto.setName(invitation.getName());
            dto.setDate(invitation.getDate());
            dto.setExpiryDate(invitation.getExpiryDate());
            dto.setReservationId(invitation.getReservationId());
            dto.setUserId(invitation.getUserId());
            return dto;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody InvitationDTO dto) {
        Invitation invitation = null;
        try{
            invitation = new Invitation();
            invitation.setName(dto.getName());
            invitation.setDate(dto.getDate());
            invitation.setExpiryDate(dto.getExpiryDate());
            invitation.setReservationId(dto.getReservationId());
            invitation.setUserId(dto.getUserId());
            service.add(invitation);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody InvitationDTO dto) {
        Invitation invitation = null;
        try{
            invitation = new Invitation();
            invitation.setId(dto.getId());
            invitation.setName(dto.getName());
            invitation.setDate(dto.getDate());
            invitation.setExpiryDate(dto.getExpiryDate());
            invitation.setReservationId(dto.getReservationId());
            invitation.setUserId(dto.getUserId());
            service.update(invitation);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }
}
