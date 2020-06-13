package com.elgendy.reservationservice.model.dto;

import java.util.Date;

public class ReservationDTO {

    private Integer id;

    private String name;

    private Date ReservedTime;

    private String PlayersNeeded;

    private String HoursNumber;

    private Integer playgroundId;

    private Integer userId;

    private PlaygroundDTO playgroundDTO;

    private UserDTO userDTO;

    public ReservationDTO() {
    }

    public ReservationDTO(Integer id, String name, Date reservedTime, String playersNeeded, String hoursNumber, Integer playgroundId, Integer userId, PlaygroundDTO playgroundDTO, UserDTO userDTO) {
        this.id = id;
        this.name = name;
        ReservedTime = reservedTime;
        PlayersNeeded = playersNeeded;
        HoursNumber = hoursNumber;
        this.playgroundId = playgroundId;
        this.userId = userId;
        this.playgroundDTO = playgroundDTO;
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

    public PlaygroundDTO getPlaygroundDTO() {
        return playgroundDTO;
    }

    public void setPlaygroundDTO(PlaygroundDTO playgroundDTO) {
        this.playgroundDTO = playgroundDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
