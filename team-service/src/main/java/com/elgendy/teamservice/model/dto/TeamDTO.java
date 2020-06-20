package com.elgendy.teamservice.model.dto;

import java.io.Serializable;

public class TeamDTO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String bio;

    public TeamDTO() {
    }

    public TeamDTO(Integer id, String name, String address, String bio) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bio = bio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
