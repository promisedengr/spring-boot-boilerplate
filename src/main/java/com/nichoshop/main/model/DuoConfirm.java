package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import lombok.Data;

@Data
@Entity
@Table(name = "duo_confirms")
public class DuoConfirm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column private Long id;
    @Column private String username;
    @Column private String state;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();

}
