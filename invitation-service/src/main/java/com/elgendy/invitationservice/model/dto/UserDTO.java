package com.elgendy.invitationservice.model.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4337178307266783091L;
    private Long id;
    private String name;
    private String address;
    private String position;
    private String phone;
    private String bio;
}
