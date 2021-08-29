package com.elgendy.invitationservice.controller;

import com.elgendy.invitationservice.exception.InternalServerErrorException;
import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.service.InvitationService;
import com.elgendy.invitationservice.service.ReservationInfo;
import com.elgendy.invitationservice.service.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

    private final InvitationService service;
    private final UserInfo userInfo;
    private final ReservationInfo reservationInfo;

    @GetMapping("/")
    @Cacheable(value= "invitationsListCache", unless= "#result.size() == 0")
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
                log.info(dto.toString());
                return dto;
            }).collect(Collectors.toList());
            return invitationDTOs;
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "invitationCache")
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(value= "InvitationCache")
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "invitationCache")
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
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value= "InvitationCache")
    public void delete(@PathVariable("id") Integer id) {
        try{
            service.delete(id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerErrorException("Error Occurred!");
        }
    }
}
