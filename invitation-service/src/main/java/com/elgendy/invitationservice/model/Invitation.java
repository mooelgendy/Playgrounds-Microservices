package com.elgendy.invitationservice.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "INVITATION")
public class Invitation {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INVITATION_DATE")
    private Date date;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;
    
    @Column(name = "RESERVATION_ID")
    private Integer reservationId;

	@Column(name = "USER_ID")
    private Integer userId;

	public Invitation() {
		super();
	}

	public Invitation(String name, Date date, Date expiryDate, Integer reservationId, Integer userId) {
		this.name = name;
		this.date = date;
		this.expiryDate = expiryDate;
		this.reservationId = reservationId;
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

	@Override
	public String toString() {
		return "Invitation{" +
				"id=" + id +
				", name='" + name + '\'' +
				", date=" + date +
				", expiryDate='" + expiryDate + '\'' +
				", reservationId=" + reservationId +
				", userId=" + userId +
				'}';
	}
}
