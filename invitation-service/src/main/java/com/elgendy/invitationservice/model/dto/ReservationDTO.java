package com.elgendy.invitationservice.model.dto;

import java.io.Serializable;
import java.util.Date;

public class ReservationDTO implements Serializable {

    private Integer id;

    private String name;

    private Date ReservedTime;

    private String PlayersNeeded;

    private String HoursNumber;

    public ReservationDTO() {
    }

    public ReservationDTO(Integer id, String name, Date reservedTime, String playersNeeded, String hoursNumber) {
        this.id = id;
        this.name = name;
        ReservedTime = reservedTime;
        PlayersNeeded = playersNeeded;
        HoursNumber = hoursNumber;
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

    public Date getReservedTime() {
        return ReservedTime;
    }

    public void setReservedTime(Date reservedTime) {
        ReservedTime = reservedTime;
    }

    public String getPlayersNeeded() {
        return PlayersNeeded;
    }

    public void setPlayersNeeded(String playersNeeded) {
        PlayersNeeded = playersNeeded;
    }

    public String getHoursNumber() {
        return HoursNumber;
    }

    public void setHoursNumber(String hoursNumber) {
        HoursNumber = hoursNumber;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ReservedTime=" + ReservedTime +
                ", PlayersNeeded='" + PlayersNeeded + '\'' +
                ", HoursNumber='" + HoursNumber + '\'' +
                '}';
    }
}
