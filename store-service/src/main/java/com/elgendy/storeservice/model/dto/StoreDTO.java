package com.elgendy.storeservice.model.dto;

import java.io.Serializable;

public class StoreDTO implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private String serialNumber;

    private String price;

    private Integer userId;

    private UserDTO userDTO;

    public StoreDTO() {
    }

    public StoreDTO(Integer id, String name, String description, String serialNumber, String price, Integer userId, UserDTO userDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.price = price;
        this.userId = userId;
        this.userDTO = userDTO;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
