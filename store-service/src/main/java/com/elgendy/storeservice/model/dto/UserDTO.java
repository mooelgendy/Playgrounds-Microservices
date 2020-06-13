package com.elgendy.storeservice.model.dto;

public class UserDTO {

    private Integer id;

    private String name;

    private String address;

    private String position;

    private String phone;

    private String bio;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String address, String position, String phone, String bio) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.position = position;
        this.phone = phone;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
