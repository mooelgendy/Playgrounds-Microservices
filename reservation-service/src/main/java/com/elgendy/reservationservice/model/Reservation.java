package com.elgendy.reservationservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RESERVED_TIME")
    private Date ReservedTime;

    @Column(name = "PLAYERS_NEEDED")
    private String PlayersNeeded;

    @Column(name = "HOURS_NUMBER")
    private String HoursNumber;

    @Column(name = "PLAYGROUND_ID")
    private Integer playgroundId;

    @JoinColumn(name = "USER_ID")
    private Integer userId;
}
