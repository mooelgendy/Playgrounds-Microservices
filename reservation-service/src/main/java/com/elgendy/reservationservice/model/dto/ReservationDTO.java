package com.elgendy.reservationservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Date ReservedTime;
    private String PlayersNeeded;
    private String HoursNumber;
    private Integer playgroundId;
    private Integer userId;
    private PlaygroundDTO playgroundDTO;
    private UserDTO userDTO;
}
