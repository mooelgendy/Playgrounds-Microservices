package com.elgendy.photoservice.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PHOTO")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "Link")
    private String link;

    @Column(name = "USER_ID")
    private Integer userId;

    @JoinColumn(name = "TEAM_ID")
    private Integer teamId;
    
    @JoinColumn(name = "STORE_ID")
    private Integer storeId;

    @JoinColumn(name = "PLAYGROUND_ID")
    private Integer playgroundId;

    public Photo() {
    }

    public Photo(String name, String link, Integer userId, Integer teamId, Integer storeId, Integer playgroundId) {
        this.name = name;
        this.link = link;
        this.userId = userId;
        this.teamId = teamId;
        this.storeId = storeId;
        this.playgroundId = playgroundId;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(Integer playgroundId) {
        this.playgroundId = playgroundId;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", userId=" + userId +
                ", teamId=" + teamId +
                ", storeId=" + storeId +
                ", playgroundId=" + playgroundId +
                '}';
    }
}
