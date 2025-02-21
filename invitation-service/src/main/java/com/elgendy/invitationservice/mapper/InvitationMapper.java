package com.elgendy.invitationservice.mapper;

import com.elgendy.invitationservice.model.Invitation;
import com.elgendy.invitationservice.model.dto.InvitationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvitationMapper {
    
    @Mapping(target = "reservationDTO", ignore = true)
    @Mapping(target = "userDTO", ignore = true)
    InvitationDTO toDto(Invitation invitation);
    
    Invitation toEntity(InvitationDTO invitationDTO);
} 