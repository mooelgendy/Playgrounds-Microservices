package com.elgendy.playgroundservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "PLAYGROUND")
public class Playground implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "AREA")
    private String area;

    @Column(name = "AVAILABLE_TIME")
    private Date availableTime;

    @Column(name = "PRICE_PER_HOUR")
    private String pricePerHour;
}
