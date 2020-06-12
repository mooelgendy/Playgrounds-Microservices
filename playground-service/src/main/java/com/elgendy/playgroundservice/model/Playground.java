package com.elgendy.playgroundservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PLAYGROUND")
public class Playground implements Serializable {

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

    public Playground() {
    }

    public Playground(String name, String address, String description, String phone, String area, Date availableTime, String pricePerHour) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
        this.area = area;
        this.availableTime = availableTime;
        this.pricePerHour = pricePerHour;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    @Override
    public String toString() {
        return "Playground{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", area='" + area + '\'' +
                ", availableTime='" + availableTime + '\'' +
                ", pricePerHour='" + pricePerHour + '\'' +
                '}';
    }
}
