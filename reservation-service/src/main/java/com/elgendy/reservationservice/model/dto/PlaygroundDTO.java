package com.elgendy.reservationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class PlaygroundDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String address;
    private String description;
    private String phone;
    private String area;
    private Date availableTime;
    private String pricePerHour;
}
