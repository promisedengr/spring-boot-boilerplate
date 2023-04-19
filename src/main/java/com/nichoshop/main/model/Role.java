package com.nichoshop.main.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column
    private Long id;
    @Column(name = "user_id")
    private Long userId;

    @Column
    private String type;

}