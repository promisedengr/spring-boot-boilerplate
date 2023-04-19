package com.nichoshop.main.model;

import javax.persistence.*;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import lombok.Data;

@Data
@Entity
@Table(name = "admin_notes")
public class AdminNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String subject;
    @Column
    private String desc;
    @Column
    private int csId;
    @Column
    private Long creatorId;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

}
