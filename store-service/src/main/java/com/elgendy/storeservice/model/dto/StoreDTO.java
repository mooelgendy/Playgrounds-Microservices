package com.elgendy.storeservice.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private String serialNumber;
    private String price;
    private Integer userId;
    private UserDTO userDTO;
}
