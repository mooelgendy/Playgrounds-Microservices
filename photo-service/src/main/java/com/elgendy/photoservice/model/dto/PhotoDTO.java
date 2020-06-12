package com.elgendy.photoservice.model.dto;

public class PhotoDTO {

    private Integer id;

    private String name;

    private String link;

    private Integer userId;

    private Integer teamId;

    private Integer storeId;

    private Integer playgroundId;

    public PhotoDTO() {
    }

    public PhotoDTO(Integer id, String name, String link, Integer userId, Integer teamId, Integer storeId, Integer playgroundId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.userId = userId;
        this.teamId = teamId;
        this.storeId = storeId;
        this.playgroundId = playgroundId;
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
}
