package com.elgendy.invitationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.service.InvitationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

	private InvitationService service;
	@Autowired
	private WebClient.Builder webClientBuilder;
    private static Logger LOGGER = LoggerFactory.getLogger(InvitationController.class);

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
                        dto.setReservationId(invitation.getReservationId());
                        dto.setUserId(invitation.getUserId());
                        return dto;
            }).collect(Collectors.toList());
            return invitationDTOs;
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public InvitationDTO findOne(@PathVariable("id") Integer id){
        Invitation invitation = null;
        InvitationDTO dto = null;
        try{
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
            throw new RuntimeException("Internal Server Error");
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
            throw new RuntimeException("Internal Server Error");
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
