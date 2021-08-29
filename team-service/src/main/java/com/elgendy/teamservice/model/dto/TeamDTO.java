package com.elgendy.teamservice.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String address;
    private String bio;
}
