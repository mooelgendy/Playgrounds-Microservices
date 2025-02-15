package com.elgendy.invitationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitationDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -8078928298584452751L;
	private Long id;
    private String name;
    private Date date;
    private Date expiryDate;
	private Integer reservationId;
	private Integer userId;
	private ReservationDTO reservationDTO;
	private UserDTO userDTO;
}
