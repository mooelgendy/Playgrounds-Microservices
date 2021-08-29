package com.elgendy.invitationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class ReservationDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Date ReservedTime;
    private String PlayersNeeded;
    private String HoursNumber;
}
