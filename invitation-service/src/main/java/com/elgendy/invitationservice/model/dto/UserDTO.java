package com.elgendy.invitationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 4337178307266783091L;
    private Integer id;
    private String name;
    private String address;
    private String position;
    private String phone;
    private String bio;
}
