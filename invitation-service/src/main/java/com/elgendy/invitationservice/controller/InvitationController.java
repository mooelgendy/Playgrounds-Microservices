package com.elgendy.invitationservice.controller;

import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.service.InvitationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/")
    @Cacheable(value= "invitationsListCache", unless= "#result.size() == 0")
    public ResponseEntity<List<InvitationDTO>> getAll() {
        List<InvitationDTO> invitationDTOs = invitationService.getInvitations();
        return new ResponseEntity<>(invitationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "invitationCache")
    public ResponseEntity<InvitationDTO> findOne(@PathVariable("id") Integer id){
        InvitationDTO invitationDTO = invitationService.getInvitation(id);
        return new ResponseEntity<>(invitationDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(value= "InvitationCache")
    public void create(@RequestBody InvitationDTO dto) {
        invitationService.addInvitation(dto);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "invitationCache")
    public void update(@RequestBody InvitationDTO dto) {
        invitationService.updateInvitation(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value= "InvitationCache")
    public void delete(@PathVariable("id") Integer id) {
        invitationService.deleteInvitation(id);
    }
}
