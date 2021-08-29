package com.elgendy.photoservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PHOTO")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
