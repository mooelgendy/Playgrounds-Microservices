package com.elgendy.invitationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "INVITATION")
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

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
