package com.elgendy.storeservice.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STORE")
public class Store implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "USER_ID")
    private Integer userId;

    public Store() {
    }

    public Store(String name, String description, String price, String serialNumber, Integer userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.serialNumber = serialNumber;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", userId=" + userId +
                '}';
    }
}
