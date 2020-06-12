package com.elgendy.reservationservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RESERVED_TIME")
    private Date ReservedTime;

    @Column(name = "PLAYERS_NEEDED")
    private String PlayersNeeded;

    @Column(name = "HOURS_NUMBER")
    private String HoursNumber;

    @Column(name = "PLAYGROUND_ID")
    private Integer playgroundId;

    @JoinColumn(name = "USER_ID")
    private Integer userId;

    public Reservation() {
    }

    public Reservation(String name, Date reservedTime, String playersNeeded, String hoursNumber, Integer playgroundId, Integer userId) {
        this.name = name;
        ReservedTime = reservedTime;
        PlayersNeeded = playersNeeded;
        HoursNumber = hoursNumber;
        this.playgroundId = playgroundId;
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

    public Integer getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(Integer playgroundId) {
        this.playgroundId = playgroundId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ReservedTime=" + ReservedTime +
                ", PlayersNeeded='" + PlayersNeeded + '\'' +
                ", HoursNumber='" + HoursNumber + '\'' +
                ", playgroundId=" + playgroundId +
                ", userId=" + userId +
                '}';
    }
}
