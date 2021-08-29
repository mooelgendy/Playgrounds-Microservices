package com.elgendy.photoservice.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhotoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String link;
    private Integer userId;
    private Integer teamId;
    private Integer storeId;
    private Integer playgroundId;
}
