package com.elgendy.invitationservice.controller;

import com.elgendy.invitationservice.model.dto.InvitationDTO;
import com.elgendy.invitationservice.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invitations")
@Tag(name = "Invitation Controller", description = "APIs for managing invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping
    @Operation(summary = "Get all invitations")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all invitations")
    public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
        log.debug("REST request to get all Invitations");
        return ResponseEntity.ok(invitationService.getInvitations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invitation by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved invitation")
    @ApiResponse(responseCode = "404", description = "Invitation not found")
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable Long id) {
        log.debug("REST request to get Invitation : {}", id);
        return ResponseEntity.ok(invitationService.getInvitation(id));
    }

    @PostMapping
    @Operation(summary = "Create new invitation")
    @ApiResponse(responseCode = "201", description = "Invitation created successfully")
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationDTO dto) {
        log.debug("REST request to save Invitation : {}", dto);
        InvitationDTO result = invitationService.addInvitation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing invitation")
    @ApiResponse(responseCode = "200", description = "Invitation updated successfully")
    @ApiResponse(responseCode = "404", description = "Invitation not found")
    public ResponseEntity<InvitationDTO> updateInvitation(
            @PathVariable Long id,
            @Valid @RequestBody InvitationDTO dto) {
        log.debug("REST request to update Invitation : {}", dto);
        dto.setId(id); // Ensure ID matches path variable
        InvitationDTO result = invitationService.updateInvitation(dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete invitation")
    @ApiResponse(responseCode = "204", description = "Invitation deleted successfully")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        log.debug("REST request to delete Invitation : {}", id);
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build();
    }
}
