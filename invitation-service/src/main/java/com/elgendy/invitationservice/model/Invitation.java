package com.elgendy.invitationservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "INVITATION")
public class Invitation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INVITATION_DATE")
    private Date date;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;

	@Column(name = "USER_ID")
    private Integer userId;
}
