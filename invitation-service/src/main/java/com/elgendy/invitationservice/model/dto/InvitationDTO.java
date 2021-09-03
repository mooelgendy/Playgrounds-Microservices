package com.elgendy.invitationservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InvitationDTO implements Serializable {

	private static final long serialVersionUID = -8078928298584452751L;
	private Integer id;
    private String name;
    private Date date;
    private Date expiryDate;
	private Integer reservationId;
	private Integer userId;
	private ReservationDTO reservationDTO;
	private UserDTO userDTO;
}
