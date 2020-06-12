package com.elgendy.playgroundservice.model.dto;

import java.util.Date;

public class PlaygroundDTO {

    private Integer id;

    private String name;

    private String address;

    private String description;

    private String phone;

    private String area;

    private Date availableTime;

    private String pricePerHour;

    public PlaygroundDTO() {
    }

    public PlaygroundDTO(Integer id, String name, String address, String description, String phone, String area, Date availableTime, String pricePerHour) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.area = area;
        this.availableTime = availableTime;
        this.pricePerHour = pricePerHour;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Date availableTime) {
        this.availableTime = availableTime;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
