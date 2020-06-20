package com.elgendy.invitationservice.model.dto;

import java.io.Serializable;
import java.util.Date;

public class InvitationDTO implements Serializable {

	private Integer id;

    private String name;

    private Date date;

    private Date expiryDate;

	private Integer reservationId;

	private Integer userId;

	private ReservationDTO reservationDTO;

	private UserDTO userDTO;

	public InvitationDTO() {
		super();
	}

	public InvitationDTO(Integer id, String name, Date date, Date expiryDate, Integer reservationId, Integer userId) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.expiryDate = expiryDate;
		this.reservationId = reservationId;
		this.userId = userId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ReservationDTO getReservationDTO() {
		return reservationDTO;
	}

	public void setReservationDTO(ReservationDTO reservationDTO) {
		this.reservationDTO = reservationDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	@Override
	public String toString() {
		return "InvitationDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", date=" + date +
				", expiryDate=" + expiryDate +
				", reservationId=" + reservationId +
				", userId=" + userId +
				", reservationDTO=" + reservationDTO +
				", userDTO=" + userDTO +
				'}';
	}
}
