package com.elgendy.invitationservice.model.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = -3002613465578685720L;
    private Long id;
    private String name;
    private Date reservedTime;
    private String playersNeeded;
    private String hoursNumber;
}
